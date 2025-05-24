package com.example.personaltasks.model

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Conversor necessário para armazenar LocalDate no banco de dados,
 * pois o Room não suporta LocalDate nativamente.
 */
class LocalDateConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    /**
     * Converte um LocalDate para String, para armazenar no banco.
     */
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    /**
     * Converte uma String armazenada no banco para LocalDate.
     */
    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }
}
