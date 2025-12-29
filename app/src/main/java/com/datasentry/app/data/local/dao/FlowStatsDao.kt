package com.datasentry.app.data.local.dao

import androidx.room.*
import com.datasentry.app.data.model.FlowStats
import com.datasentry.app.data.model.TrafficType
import kotlinx.coroutines.flow.Flow

@Dao
interface FlowStatsDao {
    
    @Query("SELECT * FROM flow_stats ORDER BY lastSeen DESC")
    fun observeAll(): Flow<List<FlowStats>>
    
    @Query("SELECT * FROM flow_stats WHERE packageName = :packageName ORDER BY lastSeen DESC")
    fun observeByPackage(packageName: String): Flow<List<FlowStats>>
    
    @Query("SELECT * FROM flow_stats ORDER BY lastSeen DESC")
    suspend fun getAll(): List<FlowStats>
    
    @Query("SELECT * FROM flow_stats WHERE packageName = :packageName ORDER BY lastSeen DESC")
    suspend fun getByPackage(packageName: String): List<FlowStats>
    
    @Query("SELECT * FROM flow_stats WHERE trafficType = :trafficType ORDER BY lastSeen DESC")
    suspend fun getByTrafficType(trafficType: TrafficType): List<FlowStats>
    
    @Query("SELECT * FROM flow_stats WHERE firstSeen >= :startTime AND firstSeen <= :endTime ORDER BY lastSeen DESC")
    suspend fun getByTimeRange(startTime: Long, endTime: Long): List<FlowStats>
    
    @Query("SELECT SUM(totalBytes) FROM flow_stats")
    suspend fun getTotalDataUsage(): Long?
    
    @Query("SELECT SUM(totalBytes) FROM flow_stats WHERE packageName = :packageName")
    suspend fun getDataUsageByPackage(packageName: String): Long?
    
    @Query("SELECT trafficType, SUM(totalBytes) as totalBytes FROM flow_stats GROUP BY trafficType")
    suspend fun getDataUsageByTrafficType(): List<TrafficTypeUsage>
    
    @Query("SELECT packageName, SUM(totalBytes) as totalBytes FROM flow_stats WHERE packageName IS NOT NULL GROUP BY packageName")
    suspend fun getDataUsageByApp(): List<AppDataUsage>
    
    @Query("SELECT * FROM flow_stats ORDER BY lastSeen DESC LIMIT 10")
    suspend fun getMostRecentFlows(): List<FlowStats>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(flowStats: FlowStats): Long
    
    @Upsert
    suspend fun upsert(flowStats: FlowStats)
    
    @Update
    suspend fun update(flowStats: FlowStats)
    
    @Query("UPDATE flow_stats SET trafficType = :trafficType WHERE id = :id")
    suspend fun updateTrafficType(id: Long, trafficType: TrafficType)
    
    @Query("DELETE FROM flow_stats")
    suspend fun deleteAll()
    
    @Query("DELETE FROM flow_stats WHERE packageName = :packageName")
    suspend fun deleteByPackage(packageName: String)
    
    @Query("DELETE FROM flow_stats WHERE firstSeen < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)
}

data class TrafficTypeUsage(
    val trafficType: TrafficType,
    val totalBytes: Long
)

data class AppDataUsage(
    val packageName: String,
    val totalBytes: Long
)
