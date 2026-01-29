package com.datasentry.app.inspector

import com.datasentry.app.data.model.TrafficType

data class TrafficSummary(
    val appUid: Int,
    val bytes: Long,
    val timestamp: Long,
    val type: TrafficType
)
