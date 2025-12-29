package com.datasentry.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasentry.app.data.local.converter.RiskLevelConverter
import com.datasentry.app.data.local.converter.TrafficTypeConverter
import com.datasentry.app.data.local.dao.AppUsageDao
import com.datasentry.app.data.local.dao.FlowStatsDao
import com.datasentry.app.data.local.dao.PacketDao
import com.datasentry.app.data.local.dao.SuspiciousEventDao
import com.datasentry.app.data.local.entity.PacketEntity
import com.datasentry.app.data.model.AppUsage
import com.datasentry.app.data.model.FlowStats
import com.datasentry.app.data.model.SuspiciousEvent

@Database(
    entities = [
        PacketEntity::class,
        AppUsage::class,
        FlowStats::class,
        SuspiciousEvent::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(TrafficTypeConverter::class, RiskLevelConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun packetDao(): PacketDao
    abstract fun appUsageDao(): AppUsageDao
    abstract fun flowStatsDao(): FlowStatsDao
    abstract fun suspiciousEventDao(): SuspiciousEventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "datasentry_db"
                )
                .fallbackToDestructiveMigration() // Hackathon: Wipe DB on schema change
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
