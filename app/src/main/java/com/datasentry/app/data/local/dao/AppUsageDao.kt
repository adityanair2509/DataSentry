package com.datasentry.app.data.local.dao

import androidx.room.*
import com.datasentry.app.data.model.AppUsage
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
    
    @Query("SELECT * FROM app_usage ORDER BY startTime DESC")
    fun observeAll(): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM app_usage WHERE packageName = :packageName ORDER BY startTime DESC")
    fun observeByPackage(packageName: String): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM app_usage ORDER BY startTime DESC")
    suspend fun getAll(): List<AppUsage>
    
    @Query("SELECT * FROM app_usage WHERE packageName = :packageName ORDER BY startTime DESC")
    suspend fun getByPackage(packageName: String): List<AppUsage>
    
    @Query("SELECT * FROM app_usage WHERE startTime >= :startTime AND startTime <= :endTime ORDER BY startTime DESC")
    suspend fun getByTimeRange(startTime: Long, endTime: Long): List<AppUsage>
    
    @Query("SELECT DISTINCT packageName FROM app_usage WHERE packageName IS NOT NULL")
    suspend fun getUniquePackages(): List<String>
    
    @Query("SELECT COUNT(DISTINCT packageName) FROM app_usage WHERE packageName IS NOT NULL")
    suspend fun getUniquePackageCount(): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appUsage: AppUsage): Long
    
    @Update
    suspend fun update(appUsage: AppUsage)
    
    @Query("DELETE FROM app_usage")
    suspend fun deleteAll()
    
    @Query("DELETE FROM app_usage WHERE packageName = :packageName")
    suspend fun deleteByPackage(packageName: String)
    
    @Query("DELETE FROM app_usage WHERE startTime < :timestamp")
    suspend fun deleteOlderThan(timestamp: Long)
}
