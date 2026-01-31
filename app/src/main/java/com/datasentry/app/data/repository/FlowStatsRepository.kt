package com.datasentry.app.data.repository

import com.datasentry.app.data.model.FlowStats
import com.datasentry.app.data.model.TrafficType
import kotlinx.coroutines.flow.Flow

interface FlowStatsRepository {
    fun observeAll(): Flow<List<FlowStats>>
    fun observeByPackage(packageName: String): Flow<List<FlowStats>>
    suspend fun getAll(): List<FlowStats>
    suspend fun getByPackage(packageName: String): List<FlowStats>
    suspend fun getByTrafficType(trafficType: TrafficType): List<FlowStats>
    suspend fun getByTimeRange(startTime: Long, endTime: Long): List<FlowStats>
    suspend fun getTotalDataUsage(): Long
    suspend fun getDataUsageByPackage(packageName: String): Long
    suspend fun getDataUsageByTrafficType(): Map<TrafficType, Long>
    suspend fun getDataUsageByApp(): Map<String, Long>
    suspend fun getTopAppsByDataUsage(limit: Int): List<Pair<String, Long>>
    suspend fun getMostRecentFlows(): List<FlowStats>
    suspend fun save(flowStats: FlowStats)
    suspend fun updateTrafficType(id: Long, trafficType: TrafficType)
    suspend fun deleteAll()
    suspend fun deleteByPackage(packageName: String)
    suspend fun deleteOlderThan(timestamp: Long)
}
