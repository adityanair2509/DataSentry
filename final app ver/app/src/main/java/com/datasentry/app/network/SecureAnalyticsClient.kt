package com.datasentry.app.network

import android.content.Context
import android.util.Log
import com.datasentry.app.data.local.entity.PacketEntity
import com.datasentry.app.security.CryptoUtils
import com.datasentry.app.security.EncryptedLogManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class SecureAnalyticsClient(
    private val context: Context
) {
    private val encryptedLogManager = EncryptedLogManager(context)
    
    companion object {
        private const val API_KEY = "datasentry-secure-api-key-2024"
        private const val DEFAULT_SERVER_URL = "http://192.168.1.100:5000" // Change to your ngrok URL
    }
    
    private var serverUrl: String = DEFAULT_SERVER_URL
    
    init {
        // Try to load server URL from preferences or use ngrok URL
        val prefs = context.getSharedPreferences("datasentry_prefs", Context.MODE_PRIVATE)
        serverUrl = prefs.getString("server_url", DEFAULT_SERVER_URL) ?: DEFAULT_SERVER_URL
        Log.d("SecureAnalyticsClient", "Using server URL: $serverUrl")
    }
    
    fun updateServerUrl(newUrl: String) {
        serverUrl = newUrl
        context.getSharedPreferences("datasentry_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("server_url", newUrl)
            .apply()
        Log.d("SecureAnalyticsClient", "Updated server URL to: $serverUrl")
    }
    
    suspend fun sendEncryptedData(packets: List<PacketEntity>): Map<String, Any> = withContext(Dispatchers.IO) {
        try {
            // First test connection without encryption
            Log.d("SecureAnalyticsClient", "Testing connection to server: $serverUrl")
            testConnection()
            
            // Convert packets to JSON
            val packetJson = packets.map { packet ->
                JSONObject().apply {
                    put("data", JSONObject().apply {
                        put("appName", packet.appName)
                        put("contentType", packet.contentType)
                        put("destIp", packet.destIp)
                        put("id", packet.id)
                        put("isRisk", packet.isRisk)
                        put("protocol", packet.protocol)
                        put("sizeBytes", packet.sizeBytes)
                        put("sourceIp", packet.sourceIp)
                        put("timestamp", packet.timestamp)
                    })
                    put("deviceId", packet.deviceId)
                    put("id", packet.id)
                    put("timestamp", packet.timestamp)
                }.toString()
            }
            
            // Encrypt batch
            val encryptedBatch = CryptoUtils.encryptBatch(context, packetJson)
            
            // Send to server
            Log.d("SecureAnalyticsClient", "Sending to server: $serverUrl")
            val response = sendToServer(encryptedBatch)
            
            // Parse response
            JSONObject(response).let { json ->
                mapOf(
                    "success" to json.optBoolean("success", false),
                    "message" to json.optString("message", ""),
                    "risk" to json.optBoolean("risk", false),
                    "riskScore" to json.optInt("riskScore", 0),
                    "analysis" to json.optString("analysis", "")
                )
            }
            
        } catch (e: Exception) {
            Log.e("SecureAnalyticsClient", "Failed to send encrypted data", e)
            mapOf(
                "success" to false,
                "message" to "Failed to analyze: ${e.message}",
                "risk" to false,
                "riskScore" to 0
            )
        }
    }
    
    suspend fun sendEncryptedBatch(packets: List<PacketEntity>): List<Map<String, Any>> = withContext(Dispatchers.IO) {
        try {
            // Convert packets to JSON
            val packetJson = packets.map { packet ->
                JSONObject().apply {
                    put("data", JSONObject().apply {
                        put("appName", packet.appName)
                        put("contentType", packet.contentType)
                        put("destIp", packet.destIp)
                        put("id", packet.id)
                        put("isRisk", packet.isRisk)
                        put("protocol", packet.protocol)
                        put("sizeBytes", packet.sizeBytes)
                        put("sourceIp", packet.sourceIp)
                        put("timestamp", packet.timestamp)
                    })
                    put("deviceId", packet.deviceId)
                    put("id", packet.id)
                    put("timestamp", packet.timestamp)
                }.toString()
            }
            
            // Encrypt batch
            val encryptedBatch = CryptoUtils.encryptBatch(context, packetJson)
            
            // Send to server
            val response = sendToServer(encryptedBatch)
            
            // Parse batch response
            val jsonResponse = JSONObject(response)
            val results = jsonResponse.optJSONArray("results")
            
            if (results != null) {
                (0 until results.length()).map { i ->
                    val result = results.getJSONObject(i)
                    mapOf(
                        "success" to result.optBoolean("success", false),
                        "risk" to result.optBoolean("risk", false),
                        "riskScore" to result.optInt("riskScore", 0),
                        "domain" to result.optString("domain", ""),
                        "analysis" to result.optString("analysis", "")
                    )
                }
            } else {
                // Fallback: return single result for all packets
                packets.map {
                    mapOf(
                        "success" to jsonResponse.optBoolean("success", false),
                        "risk" to jsonResponse.optBoolean("risk", false),
                        "riskScore" to jsonResponse.optInt("riskScore", 0),
                        "domain" to it.destIp,
                        "analysis" to jsonResponse.optString("analysis", "")
                    )
                }
            }
            
        } catch (e: Exception) {
            Log.e("SecureAnalyticsClient", "Failed to send encrypted batch", e)
            packets.map {
                mapOf(
                    "success" to false,
                    "message" to "Failed to analyze: ${e.message}",
                    "risk" to false,
                    "riskScore" to 0,
                    "domain" to it.destIp
                )
            }
        }
    }
    
    private suspend fun sendToServer(encryptedData: String): String = withContext(Dispatchers.IO) {
        val url = URL("$serverUrl/api/encrypted-dns-data")
        
        try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-API-Key", API_KEY)
            connection.doOutput = true
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            
            // Create request payload
            val payload = JSONObject().apply {
                put("encrypted_data", encryptedData)
                put("device_id", getDeviceId())
                put("timestamp", System.currentTimeMillis())
                put("entry_count", 1)
                put("encryption_version", "1.0")
            }
            
            // Send request
            connection.outputStream.use { output ->
                output.write(payload.toString().toByteArray(StandardCharsets.UTF_8))
            }
            
            // Read response
            val responseCode = connection.responseCode
            val responseBody = if (responseCode == 200) {
                BufferedReader(InputStreamReader(connection.inputStream))
                    .use { it.readText() }
            } else {
                BufferedReader(InputStreamReader(connection.errorStream))
                    .use { it.readText() }
            }
            
            Log.d("SecureAnalyticsClient", "Server response: $responseCode, body: $responseBody")
            responseBody
            
        } catch (e: Exception) {
            Log.e("SecureAnalyticsClient", "Failed to send to server", e)
            throw e
        }
    }
    
    suspend fun getServerStats(): Map<String, Any> = withContext(Dispatchers.IO) {
        try {
            val url = URL("$serverUrl/api/stats")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("X-API-Key", API_KEY)
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            if (connection.responseCode == 200) {
                val response = BufferedReader(InputStreamReader(connection.inputStream))
                    .use { it.readText() }
                
                val json = JSONObject(response)
                mapOf(
                    "totalDomains" to json.optInt("totalDomains", 0),
                    "riskDomains" to json.optInt("riskDomains", 0),
                    "storageUsed" to json.optString("storageUsed", "0 MB"),
                    "lastAnalysis" to json.optString("lastAnalysis", "Never")
                )
            } else {
                emptyMap()
            }
        } catch (e: Exception) {
            Log.e("SecureAnalyticsClient", "Failed to get server stats", e)
            emptyMap()
        }
    }
    
    suspend fun clearServerData(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$serverUrl/api/clear-data")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "DELETE"
            connection.setRequestProperty("X-API-Key", API_KEY)
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            connection.responseCode == 200
        } catch (e: Exception) {
            Log.e("SecureAnalyticsClient", "Failed to clear server data", e)
            false
        }
    }
    
    private fun getDeviceId(): String {
        return android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        ) ?: "unknown"
    }
    
    private suspend fun testConnection(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$serverUrl/api/test-connection")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-API-Key", API_KEY)
            connection.doOutput = true
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            val testData = JSONObject().apply {
                put("test", "connection")
                put("timestamp", System.currentTimeMillis())
                put("device_id", getDeviceId())
            }
            
            connection.outputStream.use { output ->
                output.write(testData.toString().toByteArray(StandardCharsets.UTF_8))
            }
            
            val responseCode = connection.responseCode
            val responseBody = if (responseCode == 200) {
                BufferedReader(InputStreamReader(connection.inputStream))
                    .use { it.readText() }
            } else {
                BufferedReader(InputStreamReader(connection.errorStream))
                    .use { it.readText() }
            }
            
            Log.d("SecureAnalyticsClient", "Test connection response: $responseCode, body: $responseBody")
            responseCode == 200
            
        } catch (e: Exception) {
            Log.e("SecureAnalyticsClient", "Test connection failed", e)
            false
        }
    }
    
    fun setServerUrl(url: String) {
        serverUrl = url
    }
    
    suspend fun clearOldLogs() {
        encryptedLogManager.clearAllLogs()
    }
}
