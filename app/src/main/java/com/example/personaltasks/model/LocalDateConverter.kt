package com.example.personaltasks.model

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Conversor para armazenar LocalDate como String no Room.
 */
class LocalDateConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }
}
