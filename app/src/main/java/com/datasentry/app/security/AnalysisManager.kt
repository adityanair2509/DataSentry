package com.datasentry.app.security

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import android.util.Log
import com.datasentry.app.data.local.entity.PacketEntity
import com.datasentry.app.network.SecureAnalyticsClient
import com.datasentry.app.notifications.NotificationService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnalysisManager(
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("analysis_settings", Context.MODE_PRIVATE)
    
    private val secureAnalyticsClient = SecureAnalyticsClient(context)
    private var virusTotalAnalyzer: VirusTotalAnalyzer? = null
    
    private val _analysisMode = MutableStateFlow(getAnalysisMode())
    val analysisMode: StateFlow<String> = _analysisMode
    
    private val _serverStats = MutableStateFlow<ServerStats?>(null)
    val serverStats: StateFlow<ServerStats?> = _serverStats
    
    init {
        // Initialize VirusTotal analyzer with provider's API key
        val providerApiKey = "f41911060e0e4dcb0d0511691e13e0599074abffa1b3f70ef56ea4937ed97b27" // Replace with your actual API key
        virusTotalAnalyzer = VirusTotalAnalyzer(context, providerApiKey)
        
        Log.d("AnalysisManager", "VirusTotal analyzer initialized with provider API key")
    }
    
    fun getAnalysisMode(): String {
        return sharedPreferences.getString("analysis_mode", "on_device") ?: "on_device"
    }
    
    fun setAnalysisMode(mode: String) {
        sharedPreferences.edit()
            .putString("analysis_mode", mode)
            .apply()
        _analysisMode.value = mode
        
        Log.d("AnalysisManager", "Analysis mode changed to: $mode")
    }
    
    suspend fun analyzePacket(packet: PacketEntity): PacketEntity = withContext(Dispatchers.IO) {
        when (_analysisMode.value) {
            "on_device" -> {
                // On-device analysis with VirusTotal
                virusTotalAnalyzer?.let { analyzer ->
                    try {
                        val result = analyzer.analyzeDomain(packet.destIp)
                        
                        // Show notification for VirusTotal results
                        when (result.status) {
                            "CLEAN" -> {
                                Log.i("AnalysisManager", "✅ VirusTotal: ${packet.destIp} is CLEAN")
                            }
                            "SUSPICIOUS" -> {
                                Log.w("AnalysisManager", "⚠️ VirusTotal: ${packet.destIp} is SUSPICIOUS")
                                Toast.makeText(context, "⚠️ Suspicious: ${packet.destIp}", Toast.LENGTH_LONG).show()
                            }
                            "MALICIOUS", "HIGHLY MALICIOUS" -> {
                                Log.e("AnalysisManager", "🚨 VirusTotal: ${packet.destIp} is MALICIOUS!")
                                Toast.makeText(context, "🚨 Malicious: ${packet.destIp}", Toast.LENGTH_LONG).show()
                            }
                        }
                        
                        // Update packet with VirusTotal results
                        packet.copy(
                            riskScore = result.riskScore,
                            analysisEngine = "VirusTotal",
                            analysisTimestamp = System.currentTimeMillis(),
                            isRisk = result.positives > 0,
                            deviceId = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID) ?: "unknown_device"
                        )
                    } catch (e: Exception) {
                        Log.e("AnalysisManager", "VirusTotal analysis failed", e)
                        packet.copy(
                            riskScore = 0,
                            analysisEngine = "VirusTotal",
                            analysisTimestamp = System.currentTimeMillis(),
                            isRisk = false,
                            deviceId = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID) ?: "unknown_device"
                        )
                    }
                } ?: packet
            }
            
            "server_deep" -> {
                // Server deep analysis
                try {
                    val result = secureAnalyticsClient.sendEncryptedData(listOf(packet))
                    
                    // Update packet with server analysis results
                    packet.copy(
                        riskScore = (result["risk"] as? Boolean)?.let { if (it) 100 else 0 } ?: 0,
                        analysisEngine = "Deep Analysis Server",
                        analysisTimestamp = System.currentTimeMillis(),
                        isRisk = result["risk"] as? Boolean ?: false,
                        deviceId = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID) ?: "unknown_device"
                    )
                } catch (e: Exception) {
                    Log.e("AnalysisManager", "Server analysis failed", e)
                    packet.copy(
                        riskScore = 0,
                        analysisEngine = "Deep Analysis Server",
                        analysisTimestamp = System.currentTimeMillis(),
                        isRisk = false,
                        deviceId = android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID) ?: "unknown_device"
                    )
                }
            }
            
            else -> packet
        }
    }
    
    suspend fun analyzeBatch(packets: List<PacketEntity>): List<PacketEntity> {
        return when (_analysisMode.value) {
            "on_device" -> {
                // Analyze each packet individually on device
                packets.map { analyzePacket(it) }
            }
            "server_deep" -> {
                // Send batch to server for deep analysis
                try {
                    sendBatchToServer(packets)
                } catch (e: Exception) {
                    Log.e("AnalysisManager", "Batch server analysis failed", e)
                    // Fallback to individual on-device analysis
                    packets.map { analyzePacket(it) }
                }
            }
            else -> packets.map { analyzePacket(it) }
        }
    }
    
    private suspend fun sendBatchToServer(packets: List<PacketEntity>): List<PacketEntity> {
        return try {
            val results = secureAnalyticsClient.sendEncryptedBatch(packets)
            
            // Map results back to packets
            packets.mapIndexed { index, packet ->
                val result = results.getOrNull(index) ?: emptyMap()
                packet.copy(
                    isRisk = result.containsKey("risk") && result["risk"] == true,
                    riskScore = result["riskScore"] as? Int ?: 0,
                    analysisEngine = "Server",
                    analysisTimestamp = System.currentTimeMillis()
                )
            }
        } catch (e: Exception) {
            Log.e("AnalysisManager", "Failed to send batch to server", e)
            throw e
        }
    }
    
    private fun isSuspiciousDomain(domain: String): Boolean {
        val suspiciousPatterns = listOf(
            ".*\\.tk$",
            ".*\\.ml$",
            ".*\\.ga$",
            ".*\\.cf$",
            ".*\\.bit$",
            ".*\\.onion$",
            ".*[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}.*" // IP addresses
        )
        
        return suspiciousPatterns.any { pattern ->
            domain.matches(Regex(pattern))
        }
    }
    
    suspend fun fetchServerStats() {
        try {
            val stats = secureAnalyticsClient.getServerStats()
            _serverStats.value = ServerStats(
                totalDomains = stats["totalDomains"] as? Int ?: 0,
                riskDomains = stats["riskDomains"] as? Int ?: 0,
                storageUsed = stats["storageUsed"] as? String ?: "0 MB",
                lastAnalysis = stats["lastAnalysis"] as? String ?: "Never"
            )
        } catch (e: Exception) {
            Log.e("AnalysisManager", "Failed to fetch server stats", e)
        }
    }
    
    suspend fun clearServerData(): Boolean {
        return try {
            secureAnalyticsClient.clearServerData()
            true
        } catch (e: Exception) {
            Log.e("AnalysisManager", "Failed to clear server data", e)
            false
        }
    }
    
    fun getAnalysisSummary(): AnalysisSummary {
        return AnalysisSummary(
            mode = _analysisMode.value,
            hasVirusTotalKey = true, // Always true since provider provides key
            serverAvailable = _serverStats.value != null
        )
    }
}

data class ServerStats(
    val totalDomains: Int,
    val riskDomains: Int,
    val storageUsed: String,
    val lastAnalysis: String
)

data class AnalysisSummary(
    val mode: String,
    val hasVirusTotalKey: Boolean,
    val serverAvailable: Boolean
)
