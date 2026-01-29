package com.datasentry.app.vpn

import android.content.Context
import android.content.pm.PackageManager
import android.net.TrafficStats
import android.util.Log

/**
 * Uses Android's TrafficStats API to get real per-app data usage.
 * 
 * This provides actual bytes sent/received per app, and can identify
 * which app is most likely generating traffic at any given moment.
 */
class TrafficStatsHelper(private val context: Context) {
    
    companion object {
        private const val TAG = "TrafficStatsHelper"
        
        // Known package names for common apps (for validation)
        val KNOWN_PACKAGES = mapOf(
            "google" to "com.google.android.gms",
            "chrome" to "com.android.chrome",
            "youtube" to "com.google.android.youtube",
            "instagram" to "com.instagram.android",
            "facebook" to "com.facebook.katana",
            "whatsapp" to "com.whatsapp",
            "snapchat" to "com.snapchat.android",
            "twitter" to "com.twitter.android",
            "tiktok" to "com.zhiliaoapp.musically",
            "spotify" to "com.spotify.music",
            "netflix" to "com.netflix.mediaclient",
            "telegram" to "org.telegram.messenger"
        )
    }
    
    data class AppTrafficInfo(
        val packageName: String,
        val appName: String,
        val uid: Int,
        val rxBytes: Long,
        val txBytes: Long,
        val totalBytes: Long,
        val recentDelta: Long = 0
    )
    
    // Cache of previous readings to calculate delta
    private val previousReadings = mutableMapOf<Int, Pair<Long, Long>>()
    
    // Track recent deltas per app
    private val recentDeltas = mutableMapOf<Int, Long>()
    
    /**
     * Get traffic stats for all installed apps.
     */
    fun getAllAppsTraffic(): List<AppTrafficInfo> {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        
        return apps.mapNotNull { appInfo ->
            try {
                val uid = appInfo.uid
                val rxBytes = TrafficStats.getUidRxBytes(uid)
                val txBytes = TrafficStats.getUidTxBytes(uid)
                
                if (rxBytes <= 0 && txBytes <= 0) return@mapNotNull null
                
                val appName = pm.getApplicationLabel(appInfo).toString()
                
                AppTrafficInfo(
                    packageName = appInfo.packageName,
                    appName = appName,
                    uid = uid,
                    rxBytes = rxBytes,
                    txBytes = txBytes,
                    totalBytes = rxBytes + txBytes,
                    recentDelta = recentDeltas[uid] ?: 0
                )
            } catch (e: Exception) {
                null
            }
        }.sortedByDescending { it.totalBytes }
    }
    
    /**
     * Get traffic delta for a specific UID.
     */
    fun getTrafficDelta(uid: Int): Pair<Long, Long> {
        val currentRx = TrafficStats.getUidRxBytes(uid)
        val currentTx = TrafficStats.getUidTxBytes(uid)
        
        val previous = previousReadings[uid] ?: (0L to 0L)
        val deltaRx = if (currentRx > previous.first) currentRx - previous.first else 0L
        val deltaTx = if (currentTx > previous.second) currentTx - previous.second else 0L
        
        previousReadings[uid] = currentRx to currentTx
        
        return deltaRx to deltaTx
    }
    
    /**
     * Get traffic for a specific package name.
     */
    fun getAppTraffic(packageName: String): AppTrafficInfo? {
        return try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            val uid = appInfo.uid
            
            val rxBytes = TrafficStats.getUidRxBytes(uid)
            val txBytes = TrafficStats.getUidTxBytes(uid)
            
            AppTrafficInfo(
                packageName = packageName,
                appName = pm.getApplicationLabel(appInfo).toString(),
                uid = uid,
                rxBytes = rxBytes,
                txBytes = txBytes,
                totalBytes = rxBytes + txBytes
            )
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Get apps that have had recent traffic.
     * Key method for correlation-based app detection.
     */
    fun getActiveApps(): List<AppTrafficInfo> {
        val pm = context.packageManager
        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val activeApps = mutableListOf<AppTrafficInfo>()
        
        for (appInfo in apps) {
            try {
                val uid = appInfo.uid
                val currentRx = TrafficStats.getUidRxBytes(uid)
                val currentTx = TrafficStats.getUidTxBytes(uid)
                
                if (currentRx <= 0 && currentTx <= 0) continue
                
                val previous = previousReadings[uid]
                if (previous != null) {
                    val deltaRx = if (currentRx > previous.first) currentRx - previous.first else 0L
                    val deltaTx = if (currentTx > previous.second) currentTx - previous.second else 0L
                    val totalDelta = deltaRx + deltaTx
                    
                    if (totalDelta > 0) {
                        recentDeltas[uid] = totalDelta
                        val appName = pm.getApplicationLabel(appInfo).toString()
                        activeApps.add(AppTrafficInfo(
                            packageName = appInfo.packageName,
                            appName = appName,
                            uid = uid,
                            rxBytes = currentRx,
                            txBytes = currentTx,
                            totalBytes = currentRx + currentTx,
                            recentDelta = totalDelta
                        ))
                    }
                }
                previousReadings[uid] = currentRx to currentTx
            } catch (e: Exception) {
                // Skip
            }
        }
        
        return activeApps.sortedByDescending { it.recentDelta }
    }
    
    /**
     * Find the most likely app that generated a network request.
     * Uses domain hints for better accuracy.
     */
    fun findMostActiveApp(domainHint: String? = null): AppTrafficInfo? {
        // If we have a domain hint, try to match it to known packages
        if (domainHint != null) {
            val lowerDomain = domainHint.lowercase()
            
            for ((pattern, packageName) in KNOWN_PACKAGES) {
                if (lowerDomain.contains(pattern)) {
                    val appInfo = getAppTraffic(packageName)
                    if (appInfo != null) {
                        Log.d(TAG, "Matched '$domainHint' to $packageName")
                        return appInfo
                    }
                }
            }
        }
        
        // Fall back to most active app by traffic delta
        val activeApps = getActiveApps()
        return activeApps.firstOrNull()?.also {
            Log.d(TAG, "Most active: ${it.packageName} (${formatBytes(it.recentDelta)})")
        }
    }
    
    /**
     * Get top N apps by data usage.
     */
    fun getTopApps(limit: Int = 10): List<AppTrafficInfo> {
        return getAllAppsTraffic().take(limit)
    }
    
    /**
     * Format bytes to human readable.
     */
    fun formatBytes(bytes: Long): String {
        return when {
            bytes >= 1024L * 1024L * 1024L -> String.format("%.1f GB", bytes / (1024f * 1024f * 1024f))
            bytes >= 1024L * 1024L -> String.format("%.1f MB", bytes / (1024f * 1024f))
            bytes >= 1024L -> String.format("%.1f KB", bytes / 1024f)
            else -> "$bytes B"
        }
    }
    
    /**
     * Check if a package is installed.
     */
    fun isPackageInstalled(packageName: String): Boolean {
        return try {
            context.packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
