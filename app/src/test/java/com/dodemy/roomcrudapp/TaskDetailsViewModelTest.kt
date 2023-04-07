package com.dodemy.roomcrudapp

import com.dodemy.roomcrudapp.ui.viewmodels.TaskDetailsViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dodemy.roomcrudapp.data.entities.Task
import com.dodemy.roomcrudapp.repository.TaskRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
class TaskDetailsViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var taskRepository: TaskRepository

    @Mock
    private lateinit var taskObserver: Observer<Task?>

    private lateinit var viewModel: TaskDetailsViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        viewModel = TaskDetailsViewModel(taskRepository).apply {
            task.observeForever(taskObserver)
        }
    }

    @Test
    fun fetchTask_existingTask() = testDispatcher.runBlockingTest {
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
    fun saveTask_updateExistingTask() = testDispatcher.runBlockingTest {
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
    fun saveTask_createNewTask() = testDispatcher.runBlockingTest {
        val title = "New Task Title"
        val description = "New Task Description"
        val isCompleted = false

        viewModel.saveTask(title, description, isCompleted)

        val newTask = Task(id = 0, title = title, description = description, isCompleted = isCompleted)
        verify(taskRepository).insertTask(newTask)
    }

//    @Test
//    fun deleteTask_existingTask() = testDispatcher.runBlockingTest {
//        val taskId = 1
//        val task = Task(id = taskId, title = "Test Task", description = "Test description")
//        viewModel.task.value = task
//
//        viewModel.deleteTask()
//
//        verify(taskRepository).deleteTask(task)
//    }

//    @Test
//    fun deleteTask_noTask() = testDispatcher.runBlockingTest {
//        viewModel.task.value = null
//
//        viewModel.deleteTask()
//
//        verifyNoMoreInteractions(taskRepository)
//    }
}

