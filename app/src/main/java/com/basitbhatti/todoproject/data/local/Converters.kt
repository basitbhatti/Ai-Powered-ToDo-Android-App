package com.basitbhatti.todoproject.data.local

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromLocalDate(date:LocalDate?):Long? = date?.toEpochDay()

    @TypeConverter
    fun toLocalDate(epoch:Long?):LocalDate? = epoch?.let { LocalDate.ofEpochDay(it) }

}