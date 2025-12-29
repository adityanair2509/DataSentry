package com.datasentry.app.data.repository

import com.datasentry.app.data.local.dao.AppUsageDao
import com.datasentry.app.data.model.AppUsage
import kotlinx.coroutines.flow.Flow

class AppUsageRepositoryImpl(
    private val appUsageDao: AppUsageDao
) : AppUsageRepository {
    
    override fun observeAll(): Flow<List<AppUsage>> {
        return appUsageDao.observeAll()
    }
    
    override fun observeByPackage(packageName: String): Flow<List<AppUsage>> {
        return appUsageDao.observeByPackage(packageName)
    }
    
    override suspend fun getAll(): List<AppUsage> {
        return appUsageDao.getAll()
    }
    
    override suspend fun getByPackage(packageName: String): List<AppUsage> {
        return appUsageDao.getByPackage(packageName)
    }
    
    override suspend fun getByTimeRange(startTime: Long, endTime: Long): List<AppUsage> {
        return appUsageDao.getByTimeRange(startTime, endTime)
    }
    
    override suspend fun getUniquePackages(): List<String> {
        return appUsageDao.getUniquePackages()
    }
    
    override suspend fun getUniquePackageCount(): Int {
        return appUsageDao.getUniquePackageCount()
    }
    
    override suspend fun save(appUsage: AppUsage): Long {
        return appUsageDao.insert(appUsage)
    }
    
    override suspend fun update(appUsage: AppUsage) {
        appUsageDao.update(appUsage)
    }
    
    override suspend fun deleteAll() {
        appUsageDao.deleteAll()
    }
    
    override suspend fun deleteByPackage(packageName: String) {
        appUsageDao.deleteByPackage(packageName)
    }
    
    override suspend fun deleteOlderThan(timestamp: Long) {
        appUsageDao.deleteOlderThan(timestamp)
    }
}
