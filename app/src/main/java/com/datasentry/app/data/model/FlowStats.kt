package com.datasentry.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flow_stats")
data class FlowStats(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val appUid: Int,
    val packageName: String? = null,
    val firstSeen: Long,
    val lastSeen: Long,
    val packetCount: Int = 0,
    val totalBytes: Long = 0,
    val trafficType: TrafficType = TrafficType.UNKNOWN
)
