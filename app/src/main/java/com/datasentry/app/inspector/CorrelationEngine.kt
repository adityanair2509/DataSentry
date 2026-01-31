package com.datasentry.app.inspector

import com.datasentry.app.data.model.TrafficType

class CorrelationEngine {
    
    fun correlate(
        foregroundApp: String?,
        traffic: List<TrafficSummary>
    ): List<CorrelationResult> {
        
        if (foregroundApp == null) return emptyList()
        
        return traffic.map { summary ->
            CorrelationResult(
                appPackage = foregroundApp,
                trafficType = summary.type,
                reason = buildReason(summary),
                timestamp = summary.timestamp
            )
        }
    }
    
    private fun buildReason(summary: TrafficSummary): String {
        return when (summary.type) {
            TrafficType.VIDEO ->
                "High bandwidth traffic while app in foreground"
            
            TrafficType.AUDIO ->
                "Continuous audio-like traffic detected"
            
            TrafficType.TEXT ->
                "Frequent small packets (analytics / messaging)"
            
            TrafficType.UNKNOWN ->
                "Low or unclassified traffic"
        }
    }
}
