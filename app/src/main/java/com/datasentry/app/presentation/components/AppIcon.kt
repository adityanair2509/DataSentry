package com.datasentry.app.presentation.components

import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap

private const val TAG = "AppIcon"

/**
 * Displays the actual app icon for a given package name.
 * Falls back to emoji if icon cannot be loaded.
 */
@Composable
fun AppIcon(
    packageName: String,
    appName: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp
) {
    val context = LocalContext.current
    var appIcon by remember(packageName) { mutableStateOf<android.graphics.Bitmap?>(null) }
    
    LaunchedEffect(packageName, appName) {
        Log.d(TAG, "🔍 Attempting to load icon for: app='$appName', package='$packageName'")
        
        try {
            if (packageName.isBlank()) {
                Log.w(TAG, "❌ Package name is BLANK for app: $appName")
                return@LaunchedEffect
            }
            
            if (packageName == "android") {
                Log.d(TAG, "⚙️ System app detected, using fallback for: $appName")
                return@LaunchedEffect
            }
            
            // Try to load the icon
            val icon = context.packageManager.getApplicationIcon(packageName)
            appIcon = icon.toBitmap(
                width = (size.value * 2).toInt(),
                height = (size.value * 2).toInt()
            )
            Log.d(TAG, "✅ SUCCESS! Loaded icon for: $appName ($packageName)")
            
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "❌ App NOT INSTALLED: $appName ($packageName)")
        } catch (e: Exception) {
            Log.e(TAG, "❌ ERROR loading icon for $appName ($packageName): ${e.message}", e)
        }
    }
    
    if (appIcon != null) {
        Image(
            bitmap = appIcon!!.asImageBitmap(),
            contentDescription = "$appName icon",
            modifier = modifier.size(size)
        )
    } else {
        // Fallback to emoji
        Text(
            text = "📱",
            fontSize = (size.value * 0.6f).sp,
            modifier = modifier
        )
    }
}
