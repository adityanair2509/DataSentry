package com.datasentry.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_usage")
data class AppUsage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String? = null,
    val startTime: Long,
    val endTime: Long? = null,
    val duration: Long = 0,
    val isForeground: Boolean = true
)
