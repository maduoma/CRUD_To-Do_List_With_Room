package com.dodemy.roomcrudapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dodemy.roomcrudapp.database.converters.DateConverter
import com.dodemy.roomcrudapp.database.converters.ReminderFrequencyConverter
import java.util.*


@Entity(tableName = "tasks")
@TypeConverters(DateConverter::class, ReminderFrequencyConverter::class)
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,

    @ColumnInfo(name = "start_date")
    val startDate: Date,

    @ColumnInfo(name = "end_date")
    val endDate: Date,

    // Add the reminderFrequency property
    @ColumnInfo(name = "reminder_frequency")
    val reminderFrequency: ReminderFrequency

) {
    // Add this function
    fun isActiveOn(date: Date): Boolean {
        return !isCompleted && startDate <= date && endDate >= date
    }

    fun shouldTriggerReminder(currentDate: Date): Boolean {
        return when (reminderFrequency) {
            ReminderFrequency.HOURLY -> true // Trigger every hour
            ReminderFrequency.DAILY -> {
                val calendar = Calendar.getInstance()
                calendar.time = currentDate
                val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
                currentHour == 0 // Trigger at 00:00 (midnight) every day
            }
            ReminderFrequency.NONE -> false
        }
    }
}
