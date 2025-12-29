package com.datasentry.app.data.repository

import com.datasentry.app.data.model.AppUsage
import kotlinx.coroutines.flow.Flow

interface AppUsageRepository {
    fun observeAll(): Flow<List<AppUsage>>
    fun observeByPackage(packageName: String): Flow<List<AppUsage>>
    suspend fun getAll(): List<AppUsage>
    suspend fun getByPackage(packageName: String): List<AppUsage>
    suspend fun getByTimeRange(startTime: Long, endTime: Long): List<AppUsage>
    suspend fun getUniquePackages(): List<String>
    suspend fun getUniquePackageCount(): Int
    suspend fun save(appUsage: AppUsage): Long
    suspend fun update(appUsage: AppUsage)
    suspend fun deleteAll()
    suspend fun deleteByPackage(packageName: String)
    suspend fun deleteOlderThan(timestamp: Long)
}
