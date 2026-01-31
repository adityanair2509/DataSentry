package com.datasentry.app.data.local.converter

import androidx.room.TypeConverter
import com.datasentry.app.data.model.RiskLevel

class RiskLevelConverter {
    @TypeConverter
    fun fromRiskLevel(value: RiskLevel): String {
        return value.name
    }

    @TypeConverter
    fun toRiskLevel(value: String): RiskLevel {
        return try {
            RiskLevel.valueOf(value)
        } catch (e: IllegalArgumentException) {
            RiskLevel.LOW
        }
    }
}
