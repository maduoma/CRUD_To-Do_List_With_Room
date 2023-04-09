package com.dodemy.roomcrudapp.workers

import kotlinx.coroutines.flow.first
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dodemy.roomcrudapp.database.AppDatabase
import com.dodemy.roomcrudapp.utils.TtsHelper
import java.util.Date

class TaskReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val ttsHelper = TtsHelper(applicationContext) {}

    override suspend fun doWork(): Result {
        val currentDate = Date()
        val taskDao = AppDatabase.getDatabase(applicationContext).taskDao()
        val activeTasks = taskDao.getActiveTasksWithEndDateGreaterThanOrEqual(currentDate).first()

        activeTasks.forEach { task ->
            if (task.isActiveOn(currentDate) && task.shouldTriggerReminder(currentDate)) {
                showTaskReminderNotification(applicationContext, task)
                ttsHelper.speak("${task.title}. ${task.description}")
            }
//            if (task.isActiveOn(currentDate)) {
//                showTaskReminderNotification(applicationContext, task)
//                ttsHelper.speak("${task.title}. ${task.description}")
//            }
        }

        ttsHelper.shutdown()

        return Result.success()
    }
}


//class TaskReminderWorker(
//    context: Context,
//    workerParams: WorkerParameters
//) : CoroutineWorker(context, workerParams) {
//
//    private lateinit var ttsHelper: TtsHelper
//
//    override suspend fun doWork(): Result {
//        val currentDate = Date()
//        val taskDao = AppDatabase.getDatabase(applicationContext).taskDao()
//        val activeTasks = taskDao.getActiveTasksWithEndDateGreaterThanOrEqual(currentDate).first()
//
//        ttsHelper = TtsHelper(applicationContext) {
//            // Initialize TTS here and speak tasks inside the onInitialized lambda
//            activeTasks.forEach { task ->
//                if (task.isActiveOn(currentDate)) {
//                    showTaskReminderNotification(applicationContext, task)
//                    ttsHelper.speak(task.title) // Speak the task title
//                }
//            }
//            ttsHelper.shutdown() // Shutdown the TTS helper after using it
//        }
//
//        return Result.success()
//    }
//}


//import kotlinx.coroutines.flow.first
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.dodemy.roomcrudapp.database.AppDatabase
//import java.util.Date
//
//class TaskReminderWorker(
//    context: Context,
//    workerParams: WorkerParameters
//) : CoroutineWorker(context, workerParams) {
//
//    override suspend fun doWork(): Result {
//        val currentDate = Date()
//        val taskDao = AppDatabase.getDatabase(applicationContext).taskDao()
//        val activeTasks = taskDao.getActiveTasksWithEndDateGreaterThanOrEqual(currentDate).first()
//
//        activeTasks.forEach { task ->
//            if (task.isActiveOn(currentDate)) {
//                showTaskReminderNotification(applicationContext, task)
//                ttsHelper.speak(task.title) // Add this line to speak the task title
//            }
//            ttsHelper.shutdown() // Shutdown the TTS helper after using it
//        }
//
//        return Result.success()
//    }
//}


//class TaskReminderWorker @AssistedInject constructor(
//    @Assisted private val context: Context,
//    @Assisted workerParams: WorkerParameters,
//    private val taskRepository: TaskRepositoryImpl
//) : CoroutineWorker(context, workerParams) {
//
//    private lateinit var tts: TextToSpeech
//
//    override suspend fun doWork(): Result {
//        val tasks = taskRepository.getActiveTasksWithEndDateGreaterThanOrEqual(Date(System.currentTimeMillis())).first()
//
//        tts = TextToSpeech(context) { status ->
//            if (status == TextToSpeech.SUCCESS) {
//                tts.language = Locale.US
//                tasks.forEach { task ->
//                    if (!task.isCompleted) {
//                        val message = "Reminder: ${task.title}. Description: ${task.description}"
//                        tts.speak(message, TextToSpeech.QUEUE_ADD, null, "TaskReminder")
//                    }
//                }
//            }
//        }
//
//        return Result.success()
//    }
//}
