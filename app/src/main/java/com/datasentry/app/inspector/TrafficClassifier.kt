package com.datasentry.app.inspector

import com.datasentry.app.data.model.FlowStats
import com.datasentry.app.data.model.TrafficType

object TrafficClassifier {
    
    fun classify(flow: FlowStats): TrafficType {
        val avgPacketSize = if (flow.packetCount > 0) {
            flow.totalBytes / flow.packetCount
        } else {
            0
        }
        
        return when {
            // Video: Large packets, high bandwidth
            avgPacketSize > 1000 && flow.totalBytes > 5000 -> TrafficType.VIDEO
            
            // Audio: Medium packets, continuous stream
            avgPacketSize in 200..1000 && flow.packetCount > 10 -> TrafficType.AUDIO
            
            // Text/Analytics: Small packets, frequent
            avgPacketSize < 200 && flow.packetCount > 5 -> TrafficType.TEXT
            
            else -> TrafficType.UNKNOWN
        }
    }
}
