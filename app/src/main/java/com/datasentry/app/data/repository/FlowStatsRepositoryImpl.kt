package com.datasentry.app.data.repository

import com.datasentry.app.data.local.dao.FlowStatsDao
import com.datasentry.app.data.model.FlowStats
import com.datasentry.app.data.model.TrafficType
import kotlinx.coroutines.flow.Flow

class FlowStatsRepositoryImpl(
    private val flowStatsDao: FlowStatsDao
) : FlowStatsRepository {
    
    override fun observeAll(): Flow<List<FlowStats>> {
        return flowStatsDao.observeAll()
    }
    
    override fun observeByPackage(packageName: String): Flow<List<FlowStats>> {
        return flowStatsDao.observeByPackage(packageName)
    }
    
    override suspend fun getAll(): List<FlowStats> {
        return flowStatsDao.getAll()
    }
    
    override suspend fun getByPackage(packageName: String): List<FlowStats> {
        return flowStatsDao.getByPackage(packageName)
    }
    
    override suspend fun getByTrafficType(trafficType: TrafficType): List<FlowStats> {
        return flowStatsDao.getByTrafficType(trafficType)
    }
    
    override suspend fun getByTimeRange(startTime: Long, endTime: Long): List<FlowStats> {
        return flowStatsDao.getByTimeRange(startTime, endTime)
    }
    
    override suspend fun getTotalDataUsage(): Long {
        return flowStatsDao.getTotalDataUsage() ?: 0L
    }
    
    override suspend fun getDataUsageByPackage(packageName: String): Long {
        return flowStatsDao.getDataUsageByPackage(packageName) ?: 0L
    }
    
    override suspend fun getDataUsageByTrafficType(): Map<TrafficType, Long> {
        return flowStatsDao.getDataUsageByTrafficType()
            .associate { it.trafficType to it.totalBytes }
    }
    
    override suspend fun getDataUsageByApp(): Map<String, Long> {
        return flowStatsDao.getDataUsageByApp()
            .associate { it.packageName to it.totalBytes }
    }
    
    override suspend fun getTopAppsByDataUsage(limit: Int): List<Pair<String, Long>> {
        return flowStatsDao.getDataUsageByApp()
            .sortedByDescending { it.totalBytes }
            .take(limit)
            .map { it.packageName to it.totalBytes }
    }
    
    override suspend fun getMostRecentFlows(): List<FlowStats> {
        return flowStatsDao.getMostRecentFlows()
    }
    
    override suspend fun save(flowStats: FlowStats) {
        flowStatsDao.upsert(flowStats)
    }
    
    override suspend fun updateTrafficType(id: Long, trafficType: TrafficType) {
        flowStatsDao.updateTrafficType(id, trafficType)
    }
    
    override suspend fun deleteAll() {
        flowStatsDao.deleteAll()
    }
    
    override suspend fun deleteByPackage(packageName: String) {
        flowStatsDao.deleteByPackage(packageName)
    }
    
    override suspend fun deleteOlderThan(timestamp: Long) {
        flowStatsDao.deleteOlderThan(timestamp)
    }
}
