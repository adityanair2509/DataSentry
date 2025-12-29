package com.datasentry.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.datasentry.app.DataSentryService

class BootCompletedReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Device booted - could auto-start DataSentry service if needed")
            // Optionally auto-start VPN service after boot
            // val serviceIntent = Intent(context, DataSentryService::class.java)
            // context?.startForegroundService(serviceIntent)
        }
    }
}
