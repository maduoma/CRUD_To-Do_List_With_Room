package com.dodemy.roomcrudapp.repository

import com.dodemy.roomcrudapp.data.entities.Task


import androidx.lifecycle.LiveData

interface TaskRepository {
    suspend fun insertTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
    fun getAllTasks(): LiveData<List<Task>>
    fun getTaskById(taskId: Int): LiveData<Task>
}
