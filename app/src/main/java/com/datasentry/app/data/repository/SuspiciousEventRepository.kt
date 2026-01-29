package com.datasentry.app.data.repository

import com.datasentry.app.data.model.RiskLevel
import com.datasentry.app.data.model.SuspiciousEvent
import com.datasentry.app.data.model.TrafficType
import kotlinx.coroutines.flow.Flow

interface SuspiciousEventRepository {
    fun observeAll(): Flow<List<SuspiciousEvent>>
    fun observeByPackage(packageName: String): Flow<List<SuspiciousEvent>>
    fun observeByRiskLevel(riskLevel: RiskLevel): Flow<List<SuspiciousEvent>>
    suspend fun getAll(): List<SuspiciousEvent>
    suspend fun getByPackage(packageName: String): List<SuspiciousEvent>
    suspend fun getByRiskLevel(riskLevel: RiskLevel): List<SuspiciousEvent>
    suspend fun getByTrafficType(trafficType: TrafficType): List<SuspiciousEvent>
    suspend fun getByTimeRange(startTime: Long, endTime: Long): List<SuspiciousEvent>
    suspend fun getCount(): Int
    suspend fun getCountByRiskLevel(riskLevel: RiskLevel): Int
    suspend fun getLatest(): SuspiciousEvent?
    suspend fun getRecent(limit: Int): List<SuspiciousEvent>
    suspend fun save(event: SuspiciousEvent): Long
    suspend fun update(event: SuspiciousEvent)
    suspend fun deleteAll()
    suspend fun deleteByPackage(packageName: String)
    suspend fun deleteOlderThan(timestamp: Long)
}
