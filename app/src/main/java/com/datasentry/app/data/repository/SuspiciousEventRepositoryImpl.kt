package com.datasentry.app.data.repository

import com.datasentry.app.data.local.dao.SuspiciousEventDao
import com.datasentry.app.data.model.RiskLevel
import com.datasentry.app.data.model.SuspiciousEvent
import com.datasentry.app.data.model.TrafficType
import kotlinx.coroutines.flow.Flow

class SuspiciousEventRepositoryImpl(
    private val suspiciousEventDao: SuspiciousEventDao
) : SuspiciousEventRepository {
    
    override fun observeAll(): Flow<List<SuspiciousEvent>> {
        return suspiciousEventDao.observeAll()
    }
    
    override fun observeByPackage(packageName: String): Flow<List<SuspiciousEvent>> {
        return suspiciousEventDao.observeByPackage(packageName)
    }
    
    override fun observeByRiskLevel(riskLevel: RiskLevel): Flow<List<SuspiciousEvent>> {
        return suspiciousEventDao.observeByRiskLevel(riskLevel)
    }
    
    override suspend fun getAll(): List<SuspiciousEvent> {
        return suspiciousEventDao.getAll()
    }
    
    override suspend fun getByPackage(packageName: String): List<SuspiciousEvent> {
        return suspiciousEventDao.getByPackage(packageName)
    }
    
    override suspend fun getByRiskLevel(riskLevel: RiskLevel): List<SuspiciousEvent> {
        return suspiciousEventDao.getByRiskLevel(riskLevel)
    }
    
    override suspend fun getByTrafficType(trafficType: TrafficType): List<SuspiciousEvent> {
        return suspiciousEventDao.getByTrafficType(trafficType)
    }
    
    override suspend fun getByTimeRange(startTime: Long, endTime: Long): List<SuspiciousEvent> {
        return suspiciousEventDao.getByTimeRange(startTime, endTime)
    }
    
    override suspend fun getCount(): Int {
        return suspiciousEventDao.getCount()
    }
    
    override suspend fun getCountByRiskLevel(riskLevel: RiskLevel): Int {
        return suspiciousEventDao.getCountByRiskLevel(riskLevel)
    }
    
    override suspend fun getLatest(): SuspiciousEvent? {
        return suspiciousEventDao.getLatest()
    }
    
    override suspend fun getRecent(limit: Int): List<SuspiciousEvent> {
        return suspiciousEventDao.getRecent(limit)
    }
    
    override suspend fun save(event: SuspiciousEvent): Long {
        return suspiciousEventDao.insert(event)
    }
    
    override suspend fun update(event: SuspiciousEvent) {
        suspiciousEventDao.update(event)
    }
    
    override suspend fun deleteAll() {
        suspiciousEventDao.deleteAll()
    }
    
    override suspend fun deleteByPackage(packageName: String) {
        suspiciousEventDao.deleteByPackage(packageName)
    }
    
    override suspend fun deleteOlderThan(timestamp: Long) {
        suspiciousEventDao.deleteOlderThan(timestamp)
    }
}
