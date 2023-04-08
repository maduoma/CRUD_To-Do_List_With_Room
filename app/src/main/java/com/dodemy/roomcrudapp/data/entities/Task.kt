package com.dodemy.roomcrudapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
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
    val endDate: Date
) {
    // Add this function
    fun isActiveOn(date: Date): Boolean {
        return !isCompleted && startDate <= date && endDate >= date
    }
}
