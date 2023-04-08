package com.dodemy.roomcrudapp.ui.viewmodels

import com.dodemy.roomcrudapp.data.entities.Task
import com.dodemy.roomcrudapp.repository.TaskRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dodemy.roomcrudapp.utils.TtsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import androidx.lifecycle.MutableLiveData


@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    val tasks: LiveData<List<Task>> = taskRepository.getAllTasks()

    // Add the following properties
    //private val activeTasks = taskRepository.getActiveTasksWithEndDateGreaterThanOrEqual(Date()).asLiveData()
    private val _activeTasks = MutableLiveData<List<Task>>()
    val activeTasks: LiveData<List<Task>>
        get() = _activeTasks
    private var ttsHelper: TtsHelper? = null

    // Add this function to initialize TTS
    fun initializeTts(context: Context, onInitialized: () -> Unit) {
        if (ttsHelper == null) {
            ttsHelper = TtsHelper(context) {
                onInitialized()
            }
        }
    }

    // Add this function to speak tasks
    // Modify this function to speak tasks
    fun speakTasks() {
        val tasksToSpeak = _activeTasks.value?.joinToString(separator = ", ") { it.title }
        tasksToSpeak?.let { ttsHelper?.speak(it) }
    }


    // Add this function to shut down TTS
    fun shutdownTts() {
        ttsHelper?.shutdown()
        ttsHelper = null
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
