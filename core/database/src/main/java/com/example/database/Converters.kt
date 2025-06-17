package com.example.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @TypeConverter
    fun fromString(value: String?): Date? {
        return value?.let { formatter.parse(it) }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return date?.let { formatter.format(it) }
    }
}
