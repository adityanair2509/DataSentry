package com.datasentry.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packets")
data class PacketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val sourceIp: String,
    val destIp: String,
    val protocol: String = "TCP",
    val sizeBytes: Int,
    val appName: String, // Heuristic: "YouTube", "Instagram", etc.
    val contentType: String = "Text", // Heuristic: "Video", "Audio", "Text"
    val isRisk: Boolean = false, // For Hackathon "Privacy Score"
    val riskScore: Int = 0, // Analysis risk score (0-100)
    val analysisEngine: String = "", // "VirusTotal", "Server", "Local"
    val analysisTimestamp: Long = 0, // When analysis was performed
    val deviceId: String = "" // Device identifier
)
