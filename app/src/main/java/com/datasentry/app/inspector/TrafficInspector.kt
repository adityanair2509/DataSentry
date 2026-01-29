package com.datasentry.app.inspector

import com.datasentry.app.data.model.FlowStats
import com.datasentry.app.data.model.RiskLevel
import com.datasentry.app.data.model.SuspiciousEvent
import com.datasentry.app.data.model.TrafficType

class TrafficInspector {
    
    private val flows: MutableMap<Int, FlowStats> = mutableMapOf()
    
    fun inspect(packet: ByteArray, appUid: Int) {
        val now = System.currentTimeMillis()
        
        val flow = flows.getOrPut(appUid) {
            FlowStats(
                appUid = appUid,
                firstSeen = now,
                lastSeen = now,
                packetCount = 0,
                totalBytes = 0
            )
        }
        
        // Update flow statistics
        val updatedFlow = flow.copy(
            packetCount = flow.packetCount + 1,
            totalBytes = flow.totalBytes + packet.size,
            lastSeen = now
        )
        
        flows[appUid] = updatedFlow
    }
    
    fun getFlowSummary(): String {
        val builder = StringBuilder()
        for (flow in flows.values) {
            builder.append(
                "UID=${flow.appUid} | packets=${flow.packetCount} | bytes=${flow.totalBytes}\n"
            )
        }
        return builder.toString()
    }
    
    fun getTrafficSummaries(): List<TrafficSummary> {
        return flows.values.map { flow ->
            TrafficSummary(
                appUid = flow.appUid,
                bytes = flow.totalBytes,
                timestamp = flow.lastSeen,
                type = TrafficClassifier.classify(flow)
            )
        }
    }
    
    fun detectSuspiciousTraffic(): List<SuspiciousEvent> {
        val events = mutableListOf<SuspiciousEvent>()
        
        for (flow in flows.values) {
            val type = TrafficClassifier.classify(flow)
            
            when (type) {
                TrafficType.AUDIO -> {
                    if (flow.totalBytes > 2000) {
                        events.add(
                            SuspiciousEvent(
                                appUid = flow.appUid,
                                trafficType = type,
                                riskLevel = RiskLevel.HIGH,
                                totalBytes = flow.totalBytes,
                                reason = "Continuous audio-like transmission",
                                timestamp = System.currentTimeMillis()
                            )
                        )
                    }
                }
                
                TrafficType.VIDEO -> {
                    if (flow.totalBytes > 5000) {
                        events.add(
                            SuspiciousEvent(
                                appUid = flow.appUid,
                                trafficType = type,
                                riskLevel = RiskLevel.HIGH,
                                totalBytes = flow.totalBytes,
                                reason = "Large video-like data upload",
                                timestamp = System.currentTimeMillis()
                            )
                        )
                    }
                }
                
                TrafficType.TEXT -> {
                    if (flow.packetCount > 20) {
                        events.add(
                            SuspiciousEvent(
                                appUid = flow.appUid,
                                trafficType = type,
                                riskLevel = RiskLevel.LOW,
                                totalBytes = flow.totalBytes,
                                reason = "Frequent background analytics traffic",
                                timestamp = System.currentTimeMillis()
                            )
                        )
                    }
                }
                
                else -> Unit
            }
        }
        return events
    }
    
    fun getFlows(): List<FlowStats> {
        return flows.values.toList()
    }
    
    fun clear() {
        flows.clear()
    }
}
