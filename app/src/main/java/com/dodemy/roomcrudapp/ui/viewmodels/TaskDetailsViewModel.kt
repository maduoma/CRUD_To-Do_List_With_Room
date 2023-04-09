package com.dodemy.roomcrudapp.ui.viewmodels

import com.dodemy.roomcrudapp.data.entities.Task
import com.dodemy.roomcrudapp.repository.TaskRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dodemy.roomcrudapp.data.entities.ReminderFrequency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _task = MutableLiveData<Task?>()
    val task: LiveData<Task?> = _task

    fun fetchTask(taskId: Int) {
        if (taskId != 0) {
            taskRepository.getTaskById(taskId).observeForever { task ->
                _task.value = task
            }
        } else {
            _task.value = null
        }
    }

    fun saveTask(title: String, description: String, isCompleted: Boolean,  startDate: Date,
                 endDate: Date,reminderFrequency: ReminderFrequency
    ) {
        val task = task.value

        if (task != null) {
            // Update existing task
            val updatedTask = task.copy(title = title, description = description, isCompleted = isCompleted, startDate = startDate,endDate = endDate, reminderFrequency = reminderFrequency)
            viewModelScope.launch {
                taskRepository.updateTask(updatedTask)
            }
        } else {
            // Create a new task
            val newTask = Task(title = title, description = description, isCompleted = isCompleted, startDate = startDate, endDate = endDate, reminderFrequency = reminderFrequency)
            viewModelScope.launch {
                taskRepository.insertTask(newTask)
            }
        }
    }
    fun deleteTask(onConfirmed: () -> Unit) {
        _task.value?.let {
            viewModelScope.launch {
                taskRepository.deleteTask(it)
                onConfirmed()
            }
        }
    }
}
