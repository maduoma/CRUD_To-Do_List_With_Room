package com.dodemy.roomcrudapp.repository

import com.dodemy.roomcrudapp.data.entities.Task
import kotlinx.coroutines.flow.Flow

import androidx.lifecycle.LiveData
import java.util.*

interface TaskRepository {
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getAllTasks(): LiveData<List<Task>>
    fun getTaskById(taskId: Int): LiveData<Task>
    fun getActiveTasksWithEndDateGreaterThanOrEqual(date: Date): Flow<List<Task>>
}
