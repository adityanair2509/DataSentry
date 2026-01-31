package com.datasentry.app.security

import android.content.Context
import android.util.Log
import com.datasentry.app.data.local.entity.PacketEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader

class VirusTotalAnalyzer(
    private val context: Context,
    private val apiKey: String
) {
    private val baseUrl = "https://www.virustotal.com/vtapi/v2"
    
    suspend fun analyzeDomain(domain: String): VirusTotalResult = withContext(Dispatchers.IO) {
        try {
            // First, check if domain exists in VirusTotal
            val domainReport = getDomainReport(domain)
            if (domainReport != null) {
                return@withContext domainReport
            }
            
            // If not found, submit for analysis
            submitDomainForAnalysis(domain)
            
            // Return a pending result
            VirusTotalResult(
                domain = domain,
                scanDate = System.currentTimeMillis(),
                positives = 0,
                total = 0,
                permalink = "",
                detectedEngines = emptyList(),
                riskScore = 0,
                status = "pending"
            )
            
        } catch (e: Exception) {
            Log.e("VirusTotal", "Failed to analyze domain: $domain", e)
            VirusTotalResult(
                domain = domain,
                scanDate = System.currentTimeMillis(),
                positives = 0,
                total = 0,
                permalink = "",
                detectedEngines = emptyList(),
                riskScore = 50,
                status = "error"
            )
        }
    }
    
    private fun getDomainReport(domain: String): VirusTotalResult? {
        val url = URL("$baseUrl/domain/report?apikey=$apiKey&domain=$domain")
        
        return try {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            if (connection.responseCode == 200) {
                val response = BufferedReader(InputStreamReader(connection.inputStream))
                    .use { it.readText() }
                
                parseDomainReport(response)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("VirusTotal", "Failed to get domain report", e)
            null
        }
    }
    
    private fun submitDomainForAnalysis(domain: String) {
        try {
            val url = URL("$baseUrl/domain/scan?apikey=$apiKey&domain=$domain")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
            connection.responseCode
            Log.d("VirusTotal", "Submitted domain for analysis: $domain")
        } catch (e: Exception) {
            Log.e("VirusTotal", "Failed to submit domain", e)
        }
    }
    
    private fun parseDomainReport(response: String): VirusTotalResult {
        val json = JSONObject(response)
        
        val positives = json.optInt("positives", 0)
        val total = json.optInt("total", 0)
        val scanDate = json.optString("scan_date", "")
        val permalink = json.optString("permalink", "")
        
        val detectedEngines = mutableListOf<String>()
        val scans = json.optJSONObject("scans")
        
        scans?.keys()?.forEach { engine ->
            val scanResult = scans.getJSONObject(engine)
            if (scanResult.optString("detected") == "true") {
                detectedEngines.add(engine)
            }
        }
        
        // Calculate risk score based on detection ratio
        val riskScore = if (total > 0) {
            (positives.toFloat() / total * 100).toInt()
        } else {
            0
        }
        
        // Add detailed feedback
        val status = when {
            positives == 0 -> "CLEAN"
            positives < 5 -> "SUSPICIOUS"
            positives < 10 -> "MALICIOUS"
            else -> "HIGHLY MALICIOUS"
        }
        
        Log.i("VirusTotal", "Domain analysis complete: $positives/$total engines detected (Status: $status)")
        
        return VirusTotalResult(
            domain = json.optString("domain", ""),
            scanDate = System.currentTimeMillis(),
            positives = positives,
            total = total,
            permalink = permalink,
            detectedEngines = detectedEngines,
            riskScore = riskScore,
            status = "completed"
        )
    }
    
    suspend fun analyzePacket(packet: PacketEntity): PacketEntity = withContext(Dispatchers.IO) {
        val domain = extractDomain(packet.destIp)
        if (domain.isNotEmpty()) {
            val result = analyzeDomain(domain)
            
            // Update packet with VirusTotal results
            packet.copy(
                isRisk = result.riskScore > 20, // Consider risky if >20% detection
                riskScore = result.riskScore,
                analysisEngine = "VirusTotal",
                analysisTimestamp = result.scanDate
            )
        } else {
            packet
        }
    }
    
    private fun extractDomain(destIp: String): String {
        // Extract domain from IP or hostname
        return if (destIp.matches(Regex("^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
            destIp
        } else {
            ""
        }
    }
}

data class VirusTotalResult(
    val domain: String,
    val scanDate: Long,
    val positives: Int,
    val total: Int,
    val permalink: String,
    val detectedEngines: List<String>,
    val riskScore: Int,
    val status: String // "completed", "pending", "error"
)
