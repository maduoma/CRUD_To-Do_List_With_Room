package com.dodemy.roomcrudapp.workers

import androidx.work.*


import android.content.Context
import java.util.concurrent.TimeUnit

fun scheduleHourlyTaskReminder(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()

    val hourlyReminderRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(1, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork("hourlyTaskReminder", ExistingPeriodicWorkPolicy.KEEP, hourlyReminderRequest)
}

fun scheduleDailyTaskReminder(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()

    val dailyReminderRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(24, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork("dailyTaskReminder", ExistingPeriodicWorkPolicy.KEEP, dailyReminderRequest)
}
