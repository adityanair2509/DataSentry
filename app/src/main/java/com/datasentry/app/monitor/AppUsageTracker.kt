package com.datasentry.app.monitor

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager

class AppUsageTracker(private val context: Context) {
    
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
    private val packageManager = context.packageManager
    
    fun getForegroundApp(): String? {
        val now = System.currentTimeMillis()
        val stats = usageStatsManager?.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            now - 1000 * 10, // Last 10 seconds
            now
        ) ?: return null
        
        // Find most recently used app
        val sortedStats = stats.sortedByDescending { it.lastTimeUsed }
        return sortedStats.firstOrNull()?.packageName
    }
    
    fun getAppName(packageName: String): String? {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}
