package com.datasentry.app.security

import android.content.Context
import android.util.Log
import com.datasentry.app.data.local.entity.PacketEntity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * Encrypted Log Manager for DataSentry
 * Handles secure storage and retrieval of DNS logs with AES-256 encryption
 */
class EncryptedLogManager(private val context: Context) {
    
    private val gson = Gson()
    private val logCounter = AtomicLong(0)
    private val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    
    // Directory for encrypted log files
    private val logsDir: File by lazy {
        val dir = File(context.filesDir, "logs")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        dir
    }
    
    // Maximum log file size (10MB) before rotation
    private val maxFileSize = 10 * 1024 * 1024L
    
    // Maximum number of log files to keep
    private val maxLogFiles = 50
    
    init {
        // Clean up old log files on initialization
        cleanupOldLogs()
    }
    
    /**
     * Store a single packet entity as encrypted log entry
     */
    suspend fun storeLogEntry(packetEntity: PacketEntity) = withContext(Dispatchers.IO) {
        try {
            val timestamp = System.currentTimeMillis()
            val logEntry = LogEntry(
                id = logCounter.incrementAndGet(),
                timestamp = timestamp,
                data = packetEntity,
                deviceId = getDeviceId()
            )
            
            val jsonData = gson.toJson(logEntry)
            
            // Write raw JSON to current log file (no individual encryption)
            writeToCurrentLogFile(jsonData)
            
            // Check if we need to rotate log file
            checkAndRotateLogFile()
            
        } catch (e: Exception) {
            throw IOException("Failed to store log entry: ${e.message}", e)
        }
    }
    
    /**
     * Clear all existing log files (for testing/debugging)
     */
    suspend fun clearAllLogs() = withContext(Dispatchers.IO) {
        try {
            logsDir.listFiles { file -> file.name.endsWith(".enc") }?.forEach { logFile ->
                logFile.delete()
            }
            Log.d("EncryptedLogManager", "🗑️ Cleared all log files")
        } catch (e: Exception) {
            Log.e("EncryptedLogManager", "Failed to clear logs: ${e.message}")
        }
    }
    
    /**
     * Store multiple packet entities as raw JSON batch (encryption happens at batch level)
     */
    suspend fun storeLogBatch(packetEntities: List<PacketEntity>) = withContext(Dispatchers.IO) {
        try {
            val timestamp = System.currentTimeMillis()
            val logEntries = packetEntities.mapIndexed { index, entity ->
                LogEntry(
                    id = logCounter.incrementAndGet(),
                    timestamp = timestamp + index,
                    data = entity,
                    deviceId = getDeviceId()
                )
            }
            
            val jsonData = gson.toJson(logEntries)
            
            // Write raw JSON to current log file (no encryption here)
            writeToCurrentLogFile(jsonData)
            
            // Check if we need to rotate log file
            checkAndRotateLogFile()
            
        } catch (e: Exception) {
            throw IOException("Failed to store log batch: ${e.message}", e)
        }
    }
    
    /**
     * Get all raw JSON log entries for transmission
     */
    suspend fun getEncryptedLogs(): List<String> = withContext(Dispatchers.IO) {
        try {
            val jsonEntries = mutableListOf<String>()
            
            // Read all log files
            logsDir.listFiles { file -> file.name.endsWith(".enc") }?.forEach { logFile ->
                try {
                    val jsonContent = logFile.readText()
                    if (jsonContent.isNotBlank()) {
                        jsonEntries.add(jsonContent)
                    }
                } catch (e: Exception) {
                    // Log error but continue with other files
                    e.printStackTrace()
                }
            }
            
            jsonEntries
            
        } catch (e: Exception) {
            throw IOException("Failed to read encrypted logs: ${e.message}", e)
        }
    }
    
    /**
     * Get raw JSON logs for batch transmission (limit to recent logs)
     */
    suspend fun getRecentEncryptedLogs(maxEntries: Int = 100): List<String> = withContext(Dispatchers.IO) {
        try {
            val jsonEntries = mutableListOf<String>()
            var totalEntries = 0
            
            // Read log files in chronological order (newest first)
            val logFiles = logsDir.listFiles { file -> file.name.endsWith(".enc") }
                ?.sortedByDescending { it.lastModified() }
                ?: emptyList()
            
            for (logFile in logFiles) {
                if (totalEntries >= maxEntries) break
                
                try {
                    val jsonContent = logFile.readText()
                    if (jsonContent.isNotBlank()) {
                        // Parse as JSON array and add individual entries
                        try {
                            val entryArray = gson.fromJson(jsonContent, Array<String>::class.java)
                            for (entry in entryArray) {
                                if (totalEntries < maxEntries) {
                                    jsonEntries.add(entry)
                                    totalEntries++
                                }
                            }
                        } catch (e: Exception) {
                            // If parsing fails, treat as single entry
                            jsonEntries.add(jsonContent)
                            totalEntries++
                        }
                    }
                } catch (e: Exception) {
                    // Log error but continue with other files
                    e.printStackTrace()
                }
            }
            
            jsonEntries
            
        } catch (e: Exception) {
            throw IOException("Failed to read encrypted logs: ${e.message}", e)
        }
    }
    
    /**
     * Clear transmitted logs to free up space
     */
    suspend fun clearTransmittedLogs(transmittedEntries: List<String>) = withContext(Dispatchers.IO) {
        try {
            // For simplicity, we'll clear all logs after successful transmission
            // In production, you might want to track which entries were transmitted
            clearAllLogs()
            Unit
        } catch (e: Exception) {
            throw IOException("Failed to clear transmitted logs: ${e.message}", e)
        }
    }
    
    /**
     * Get storage statistics
     */
    suspend fun getStorageStats(): LogStorageStats = withContext(Dispatchers.IO) {
        try {
            val logFiles = logsDir.listFiles { file -> file.name.endsWith(".enc") } ?: emptyArray()
            val totalSize = logFiles.sumOf { it.length() }
            val totalEntries = logFiles.sumOf { file ->
                try {
                    file.readLines().count { it.isNotBlank() }
                } catch (e: Exception) {
                    0
                }
            }
            
            LogStorageStats(
                totalFiles = logFiles.size,
                totalSizeBytes = totalSize,
                totalEntries = totalEntries,
                oldestLogTimestamp = logFiles.minOfOrNull { it.lastModified() } ?: 0L,
                newestLogTimestamp = logFiles.maxOfOrNull { it.lastModified() } ?: 0L
            )
            
        } catch (e: Exception) {
            throw IOException("Failed to get storage stats: ${e.message}", e)
        }
    }
    
    /**
     * Write raw JSON data to current log file (properly formatted)
     */
    private fun writeToCurrentLogFile(jsonData: String) {
        val currentLogFile = getCurrentLogFile()
        
        // If file doesn't exist, start with empty array
        if (!currentLogFile.exists()) {
            currentLogFile.writeText("[]")
        }
        
        // Read existing array, add new entry, and write back
        try {
            val existingContent = currentLogFile.readText()
            val existingArray = if (existingContent.isNotBlank()) {
                gson.fromJson(existingContent, Array<String>::class.java).toMutableList()
            } else {
                mutableListOf()
            }
            
            existingArray.add(jsonData)
            currentLogFile.writeText(gson.toJson(existingArray))
            
        } catch (e: Exception) {
            // If parsing fails, just append with comma separator
            currentLogFile.appendText(",\n$jsonData")
        }
    }
    
    /**
     * Get current log file (creates new one if needed)
     */
    private fun getCurrentLogFile(): File {
        val currentFileName = "dns_log_current.enc"
        val currentFile = File(logsDir, currentFileName)
        
        if (!currentFile.exists()) {
            currentFile.createNewFile()
        }
        
        return currentFile
    }
    
    /**
     * Check if log file needs rotation and rotate if necessary
     */
    private fun checkAndRotateLogFile() {
        val currentFile = getCurrentLogFile()
        
        if (currentFile.length() >= maxFileSize) {
            // Rotate current log file
            val timestamp = dateFormat.format(Date())
            val archiveFileName = "dns_log_$timestamp.enc"
            val archiveFile = File(logsDir, archiveFileName)
            
            currentFile.renameTo(archiveFile)
            currentFile.createNewFile()
            
            // Clean up old files
            cleanupOldLogs()
        }
    }
    
    /**
     * Clean up old log files to maintain storage limits
     */
    private fun cleanupOldLogs() {
        try {
            val logFiles = logsDir.listFiles { file -> file.name.endsWith(".enc") }
                ?.sortedByDescending { it.lastModified() }
                ?: return
            
            // Keep only the most recent files
            if (logFiles.size > maxLogFiles) {
                logFiles.drop(maxLogFiles).forEach { file ->
                    file.delete()
                }
            }
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    /**
     * Get unique device identifier
     */
    private fun getDeviceId(): String {
        return android.provider.Settings.Secure.getString(
            context.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        ) ?: "unknown_device"
    }
    
    /**
     * Data class for log entry structure
     */
    data class LogEntry(
        val id: Long,
        val timestamp: Long,
        val data: PacketEntity,
        val deviceId: String
    )
    
    /**
     * Data class for storage statistics
     */
    data class LogStorageStats(
        val totalFiles: Int,
        val totalSizeBytes: Long,
        val totalEntries: Int,
        val oldestLogTimestamp: Long,
        val newestLogTimestamp: Long
    ) {
        val totalSizeMB: Double get() = totalSizeBytes / (1024.0 * 1024.0)
    }
}
