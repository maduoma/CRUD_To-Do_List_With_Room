package com.dodemy.roomcrudapp.repository

import com.dodemy.roomcrudapp.data.dao.TaskDao
import com.dodemy.roomcrudapp.data.entities.Task

import androidx.lifecycle.LiveData
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun insertTask(task: Task): Long {
        return taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks()
    }

    override fun getTaskById(taskId: Int): LiveData<Task> {
        return taskDao.getTaskById(taskId)
    }
}
