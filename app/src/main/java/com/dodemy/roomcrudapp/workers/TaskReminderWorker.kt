package com.dodemy.roomcrudapp.workers

import kotlinx.coroutines.flow.first
//import com.dodemy.roomcrudapp.repository.TaskRepositoryImpl
import android.content.Context
//import android.speech.tts.TextToSpeech
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dodemy.roomcrudapp.database.AppDatabase
//import dagger.assisted.Assisted
//import dagger.assisted.AssistedInject
//import java.util.Locale
import java.util.Date

//import com.dodemy.roomcrudapp.data.AppDatabase
//import com.dodemy.roomcrudapp.util.showTaskReminderNotification
//import kotlinx.coroutines.flow.first
//import java.util.*

class TaskReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val currentDate = Date()
        val taskDao = AppDatabase.getDatabase(applicationContext).taskDao()
        val activeTasks = taskDao.getActiveTasksWithEndDateGreaterThanOrEqual(currentDate).first()

        activeTasks.forEach { task ->
            if (task.isActiveOn(currentDate)) {
                showTaskReminderNotification(applicationContext, task)
            }
        }

        return Result.success()
    }
}


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
