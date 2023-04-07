package com.dodemy.roomcrudapp

import com.dodemy.roomcrudapp.repository.TaskRepositoryImpl

import com.dodemy.roomcrudapp.data.dao.TaskDao
import com.dodemy.roomcrudapp.data.entities.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TaskRepositoryImplTest {

    @Mock
    private lateinit var taskDao: TaskDao

    private lateinit var taskRepository: TaskRepositoryImpl

    @Before
    fun setUp() {
        taskRepository = TaskRepositoryImpl(taskDao)
    }

    @Test
    fun insertTask() = runTest {
        val task = Task(id = 0, title = "Test Task", description = "Test description")

        taskRepository.insertTask(task)

        verify(taskDao).insertTask(task)
    }

    @Test
    fun updateTask() = runTest {
        val task = Task(id = 1, title = "Test Task", description = "Test description")

        taskRepository.updateTask(task)

        verify(taskDao).updateTask(task)
    }

    @Test
    fun deleteTask() = runTest {
        val task = Task(id = 1, title = "Test Task", description = "Test description")

        taskRepository.deleteTask(task)

        verify(taskDao).deleteTask(task)
    }

    @Test
    fun getAllTasks() {
        taskRepository.getAllTasks()

        verify(taskDao).getAllTasks()
    }

    @Test
    fun getTaskById() {
        val taskId = 1

        taskRepository.getTaskById(taskId)

        verify(taskDao).getTaskById(taskId)
    }
}
