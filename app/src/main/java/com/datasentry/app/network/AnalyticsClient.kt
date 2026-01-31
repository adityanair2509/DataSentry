package com.datasentry.app.network

import android.content.Context
import android.util.Log
import com.datasentry.app.data.local.entity.PacketEntity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Client to send DNS data to Linux analysis server
 */
class AnalyticsClient(private val context: Context) {
    
    companion object {
        private const val TAG = "AnalyticsClient"
        // Configure your Linux server IP and port
        private const val SERVER_URL = "https://thomasine-hyperdulic-gilda.ngrok-free.dev/api/dns-data"  // Your ngrok URL
        private const val API_KEY = "datasentry-quick-api-key-12345"
        private const val BATCH_SIZE = 10
        private const val SEND_INTERVAL_MS = 5000L // 5 seconds
    }
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .hostnameVerifier { hostname: String, session: SSLSession -> true }
        .build()
    
    private val gson = Gson()
    private val pendingPackets = mutableListOf<PacketEntity>()
    private val scope = CoroutineScope(Dispatchers.IO)
    
    data class DnsQuery(
        val timestamp: Long,
        val domain: String,
        val appName: String,
        val sourceIp: String,
        val destIp: String,
        val protocol: String,
        val sizeBytes: Int,
        val isRisk: Boolean
    )
    
    data class BatchDnsData(
        val deviceId: String,
        val queries: List<DnsQuery>,
        val metadata: Map<String, Any> = emptyMap()
    )
    
    /**
     * Add DNS query to batch queue
     */
    fun addDnsQuery(packet: PacketEntity) {
        synchronized(pendingPackets) {
            pendingPackets.add(packet)
            
            if (pendingPackets.size >= BATCH_SIZE) {
                sendBatch()
            }
        }
    }
    
    /**
     * Start periodic batch sending
     */
    fun startPeriodicSending() {
        scope.launch {
            while (true) {
                kotlinx.coroutines.delay(SEND_INTERVAL_MS)
                sendBatch()
            }
        }
    }
    
    private fun sendBatch() {
        val batchToSend: List<PacketEntity>
        synchronized(pendingPackets) {
            if (pendingPackets.isEmpty()) return
            batchToSend = pendingPackets.toList()
            pendingPackets.clear()
        }
        
        val dnsQueries = batchToSend.map { packet ->
            DnsQuery(
                timestamp = packet.timestamp,
                domain = packet.destIp,  // Use the domain directly (set in logDnsQuery)
                appName = packet.appName,
                sourceIp = packet.sourceIp,
                destIp = packet.destIp,
                protocol = packet.protocol,
                sizeBytes = packet.sizeBytes,
                isRisk = packet.isRisk
            )
        }
        
        val batchData = BatchDnsData(
            deviceId = getDeviceId(),
            queries = dnsQueries,
            metadata = mapOf(
                "app_version" to getAppVersion(),
                "os_version" to android.os.Build.VERSION.RELEASE,
                "device_model" to android.os.Build.MODEL
            )
        )
        
        val json = gson.toJson(batchData)
        val requestBody = json.toRequestBody("application/json".toMediaType())
        
        val request = Request.Builder()
            .url(SERVER_URL)
            .post(requestBody)
            .addHeader("User-Agent", "DataSentry-Android/1.0")
            .addHeader("X-API-Key", getApiKey())
            .build()
        
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Failed to send DNS data to server: ${e.message}", e)
                Log.e(TAG, "Server URL: $SERVER_URL")
            }
            
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Successfully sent ${batchToSend.size} DNS queries to server")
                    Log.d(TAG, "Response code: ${response.code}")
                } else {
                    Log.e(TAG, "Server returned error: ${response.code} - ${response.message}")
                    Log.e(TAG, "Response body: ${response.body?.string()}")
                }
                response.close()
            }
        })
    }
    
    private fun getDeviceId(): String {
        return android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        ) ?: "unknown-device"
    }
    
    private fun getAppVersion(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName ?: "unknown"
        } catch (e: Exception) {
            "unknown"
        }
    }
    
    private fun getApiKey(): String {
        // Store this securely - preferably in encrypted preferences
        return "datasentry-quick-api-key-12345"
    }
}
