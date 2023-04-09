package com.dodemy.roomcrudapp.database.converters


import androidx.room.TypeConverter
import com.dodemy.roomcrudapp.data.entities.ReminderFrequency

class ReminderFrequencyConverter {
    @TypeConverter
    fun toReminderFrequency(value: String): ReminderFrequency {
        return ReminderFrequency.valueOf(value)
    }

    @TypeConverter
    fun fromReminderFrequency(value: ReminderFrequency): String {
        return value.name
    }
}
