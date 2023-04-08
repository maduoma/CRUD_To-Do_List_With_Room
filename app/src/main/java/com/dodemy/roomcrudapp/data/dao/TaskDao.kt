package com.dodemy.roomcrudapp.data.dao

import com.dodemy.roomcrudapp.data.entities.Task


import androidx.lifecycle.LiveData
import androidx.room.*

import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    // Add this query
    // Update the query with the correct column names
    @Query("SELECT * FROM tasks WHERE is_completed = 0 AND end_date >= :date")
    fun getActiveTasksWithEndDateGreaterThanOrEqual(date: Date): Flow<List<Task>>

}

