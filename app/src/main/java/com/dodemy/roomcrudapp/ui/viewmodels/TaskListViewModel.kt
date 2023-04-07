package com.dodemy.roomcrudapp.ui.viewmodels

import com.dodemy.roomcrudapp.data.entities.Task
import com.dodemy.roomcrudapp.repository.TaskRepository


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tasks: LiveData<List<Task>> = taskRepository.getAllTasks()

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
