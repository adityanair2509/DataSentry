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
    val appName: String,
    val packageName: String = "", // Real package name from TrafficStats
    val contentType: String = "Text",
    val isRisk: Boolean = false
)
