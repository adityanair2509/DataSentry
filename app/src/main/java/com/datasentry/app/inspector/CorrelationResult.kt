package com.datasentry.app.inspector

import com.datasentry.app.data.model.TrafficType

data class CorrelationResult(
    val appPackage: String,
    val trafficType: TrafficType,
    val reason: String,
    val timestamp: Long
)
