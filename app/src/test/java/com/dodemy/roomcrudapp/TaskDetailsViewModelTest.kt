package com.dodemy.roomcrudapp

import com.dodemy.roomcrudapp.ui.viewmodels.TaskDetailsViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dodemy.roomcrudapp.data.entities.Task
import com.dodemy.roomcrudapp.repository.TaskRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner



@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TaskDetailsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var taskRepository: TaskRepository

    @Mock
    private lateinit var taskObserver: Observer<Task?>

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: TaskDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TaskDetailsViewModel(taskRepository)
        viewModel.task.observeForever(taskObserver)
    }

    @After
    fun tearDown() {
        viewModel.task.removeObserver(taskObserver)
        Dispatchers.resetMain()
    }


    @Test
    fun fetchTask_existingTask() = runTest {
        val taskId = 1
        val task = Task(id = taskId, title = "Test Task", description = "Test description")
        val taskLiveData = MutableLiveData<Task>()
        taskLiveData.value = task

        `when`(taskRepository.getTaskById(taskId)).thenReturn(taskLiveData)

        viewModel.fetchTask(taskId)

        assertEquals(task, viewModel.task.value)
    }


    @Test
    fun fetchTask_newTask() {
        viewModel.fetchTask(0)

        verify(taskObserver).onChanged(null)
    }

    @Test
    fun saveTask_updateExistingTask() = runTest {
        val taskId = 1
        val task = Task(id = taskId, title = "Test Task", description = "Test description")
        val updatedTitle = "Updated Task"
        val updatedDescription = "Updated description"
        val updatedTask = task.copy(title = updatedTitle, description = updatedDescription)
        val taskLiveData = MutableLiveData<Task>()
        taskLiveData.value = task

        `when`(taskRepository.getTaskById(taskId)).thenReturn(taskLiveData)

        viewModel.fetchTask(taskId)

        // Update the task
        viewModel.saveTask(updatedTitle, updatedDescription, task.isCompleted)

        // Verify the task was updated
        verify(taskRepository).updateTask(updatedTask)
    }

    @Test
    fun saveTask_createNewTask() = runTest {
        val title = "New Task Title"
        val description = "New Task Description"
        val isCompleted = false

        viewModel.saveTask(title, description, isCompleted)

        val newTask = Task(id = 0, title = title, description = description, isCompleted = isCompleted)
        verify(taskRepository).insertTask(newTask)
    }

    @Test
    fun deleteTask_existingTask() = runTest {
        val taskId = 1
        val task = Task(id = taskId, title = "Test Task", description = "Test description")
        val taskLiveData = MutableLiveData<Task>()
        taskLiveData.value = task
        `when`(taskRepository.getTaskById(taskId)).thenReturn(taskLiveData)

        viewModel.fetchTask(taskId)
        viewModel.deleteTask()

        verify(taskRepository).deleteTask(task)
    }

    @Test
    fun deleteTask_noTask() = runTest {
        // Set the task LiveData value to null
        val taskLiveData = MutableLiveData<Task>()
        taskLiveData.value = null
        `when`(taskRepository.getTaskById(anyInt())).thenReturn(taskLiveData)

        // Attempt to delete the task
        viewModel.deleteTask()

        // Verify the deleteTask method was not called
        verify(taskRepository, never()).deleteTask(any())
    }
}

