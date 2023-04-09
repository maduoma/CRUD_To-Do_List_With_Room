package com.dodemy.roomcrudapp.workers

import androidx.work.*


import android.content.Context
import java.util.concurrent.TimeUnit

fun scheduleTaskReminder(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()

    val reminderRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(1, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork("taskReminder", ExistingPeriodicWorkPolicy.KEEP, reminderRequest)
}

fun scheduleTaskReminderEvery10Seconds(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()

    val reminderRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(10, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork("taskReminderEvery10Seconds", ExistingPeriodicWorkPolicy.KEEP, reminderRequest)
}


//fun scheduleHourlyTaskReminder(context: Context) {
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
//        .build()
//
//    val hourlyReminderRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(1, TimeUnit.HOURS)
//        .setConstraints(constraints)
//        .build()
//
//    WorkManager.getInstance(context)
//        .enqueueUniquePeriodicWork("hourlyTaskReminder", ExistingPeriodicWorkPolicy.KEEP, hourlyReminderRequest)
//}
//
//fun scheduleDailyTaskReminder(context: Context) {
//    val constraints = Constraints.Builder()
//        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
//        .build()
//
//    val dailyReminderRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(24, TimeUnit.HOURS)
//        .setConstraints(constraints)
//        .build()
//
//    WorkManager.getInstance(context)
//        .enqueueUniquePeriodicWork("dailyTaskReminder", ExistingPeriodicWorkPolicy.KEEP, dailyReminderRequest)
//}
