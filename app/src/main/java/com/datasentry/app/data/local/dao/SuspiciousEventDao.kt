package com.datasentry.app.data.local.dao

import androidx.room.*
import com.datasentry.app.data.model.RiskLevel
import com.datasentry.app.data.model.SuspiciousEvent
import com.datasentry.app.data.model.TrafficType
import kotlinx.coroutines.flow.Flow

@Dao
interface SuspiciousEventDao {
    
    @Query("SELECT * FROM suspicious_events ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<SuspiciousEvent>>
    
    @Query("SELECT * FROM suspicious_events WHERE packageName = :packageName ORDER BY timestamp DESC")
    fun observeByPackage(packageName: String): Flow<List<SuspiciousEvent>>
    
    @Query("SELECT * FROM suspicious_events WHERE riskLevel = :riskLevel ORDER BY timestamp DESC")
    fun observeByRiskLevel(riskLevel: RiskLevel): Flow<List<SuspiciousEvent>>
    
    @Query("SELECT * FROM suspicious_events ORDER BY timestamp DESC")
    suspend fun getAll(): List<SuspiciousEvent>
    
    @Query("SELECT * FROM suspicious_events WHERE packageName = :packageName ORDER BY timestamp DESC")
    suspend fun getByPackage(packageName: String): List<SuspiciousEvent>
    
    @Query("SELECT * FROM suspicious_events WHERE riskLevel = :riskLevel ORDER BY timestamp DESC")
    suspend fun getByRiskLevel(riskLevel: RiskLevel): List<SuspiciousEvent>
    
    @Query("SELECT * FROM suspicious_events WHERE trafficType = :trafficType ORDER BY timestamp DESC")
    suspend fun getByTrafficType(trafficType: TrafficType): List<SuspiciousEvent>
    
    @Query("SELECT * FROM suspicious_events WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    suspend fun getByTimeRange(startTime: Long, endTime: Long): List<SuspiciousEvent>
    
    @Query("SELECT COUNT(*) FROM suspicious_events")
    suspend fun getCount(): Int
    
    @Query("SELECT COUNT(*) FROM suspicious_events WHERE riskLevel = :riskLevel")
    suspend fun getCountByRiskLevel(riskLevel: RiskLevel): Int
    
    @Query("SELECT * FROM suspicious_events ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatest(): SuspiciousEvent?
    
    @Query("SELECT * FROM suspicious_events ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecent(limit: Int): List<SuspiciousEvent>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: SuspiciousEvent): Long
    
    @Update
    suspend fun update(event: SuspiciousEvent)
    
    @Query("DELETE FROM suspicious_events")
    suspend fun deleteAll()
    
    @Query("DELETE FROM suspicious_events WHERE packageName = :packageName")
    suspend fun deleteByPackage(packageName: String)
    
    @Query("DELETE FROM suspicious_events WHERE timestamp < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)
}
