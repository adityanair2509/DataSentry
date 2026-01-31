package com.datasentry.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.datasentry.app.MainActivity
import com.datasentry.app.R
import com.datasentry.app.data.local.entity.PacketEntity

class NotificationService(private val context: Context) {
    
    companion object {
        private const val CHANNEL_ID = "datasentry_security_alerts"
        private const val CHANNEL_NAME = "Security Alerts"
        private const val CHANNEL_DESCRIPTION = "DNS security analysis alerts"
    }
    
    init {
        createNotificationChannel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    fun showThreatAlert(packet: PacketEntity, riskScore: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val severity = when {
            riskScore >= 80 -> "🔴 CRITICAL"
            riskScore >= 60 -> "🟠 HIGH"
            riskScore >= 40 -> "🟡 MEDIUM"
            else -> "🟢 LOW"
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_security_alert)
            .setContentTitle("Security Threat Detected")
            .setContentText("$severity: ${packet.destIp}")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("App: ${packet.appName}\n" +
                        "Domain: ${packet.destIp}\n" +
                        "Risk Score: $riskScore%\n" +
                        "Severity: $severity"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_view_details, "View Details", pendingIntent)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notification)
        }
    }
    
    fun showVpnStoppedAlert(riskyApps: List<PacketEntity>) {
        if (riskyApps.isEmpty()) return
        
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("show_risky_apps", true)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val riskyDomains = riskyApps.map { it.destIp }.distinct().take(3)
        val domainsText = if (riskyDomains.size > 3) {
            "${riskyDomains.joinToString(", ")} and ${riskyApps.size - 3} more"
        } else {
            riskyDomains.joinToString(", ")
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_vpn_alert)
            .setContentTitle("VPN Stopped - Risky Activity Detected")
            .setContentText("Found ${riskyApps.size} potentially dangerous connections")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Suspicious domains: $domainsText\n" +
                        "Total risky connections: ${riskyApps.size}\n" +
                        "Tap to review and take action"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_review, "Review Apps", pendingIntent)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notification)
        }
    }
    
    fun showAnalysisModeChanged(newMode: String) {
        val modeText = when (newMode) {
            "on_device" -> "On-Device Analysis (VirusTotal)"
            "server_deep" -> "Deep Analysis Server"
            else -> "Unknown Mode"
        }
        
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_settings)
            .setContentTitle("Analysis Mode Changed")
            .setContentText("Switched to: $modeText")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Your DNS analysis mode has been changed to:\n\n$modeText\n\n" +
                        "This affects how your DNS queries are analyzed and where your data is processed."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notification)
        }
    }
    
    fun showServerStatsUpdate(stats: Map<String, Any>) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("show_server_stats", true)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_server)
            .setContentTitle("Server Analysis Complete")
            .setContentText("Analyzed ${stats["totalDomains"]} domains")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Server Analysis Results:\n\n" +
                        "Total Domains: ${stats["totalDomains"]}\n" +
                        "Risk Domains: ${stats["riskDomains"]}\n" +
                        "Storage Used: ${stats["storageUsed"]}\n" +
                        "Last Analysis: ${stats["lastAnalysis"]}\n\n" +
                        "Tap to view detailed results"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        
        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notification)
        }
    }
}
