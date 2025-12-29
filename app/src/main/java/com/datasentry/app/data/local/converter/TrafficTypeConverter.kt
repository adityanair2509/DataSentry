package com.datasentry.app.data.local.converter

import androidx.room.TypeConverter
import com.datasentry.app.data.model.TrafficType

class TrafficTypeConverter {
    @TypeConverter
    fun fromTrafficType(value: TrafficType): String {
        return value.name
    }

    @TypeConverter
    fun toTrafficType(value: String): TrafficType {
        return try {
            TrafficType.valueOf(value)
        } catch (e: IllegalArgumentException) {
            TrafficType.UNKNOWN
        }
    }
}
