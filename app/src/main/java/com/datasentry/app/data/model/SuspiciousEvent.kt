package com.datasentry.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suspicious_events")
data class SuspiciousEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val appUid: Int,
    val packageName: String? = null,
    val trafficType: TrafficType,
    val riskLevel: RiskLevel,
    val totalBytes: Long,
    val reason: String,
    val timestamp: Long,
    val protocol: String? = null,
    val destinationPort: Int? = null
)
