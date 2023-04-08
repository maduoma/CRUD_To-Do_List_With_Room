package com.dodemy.roomcrudapp

import android.app.Application
import com.dodemy.roomcrudapp.workers.scheduleDailyTaskReminder
import com.dodemy.roomcrudapp.workers.scheduleHourlyTaskReminder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    // No additional code needed here, but you can add custom app-level logic if necessary.
    override fun onCreate() {
        super.onCreate()
        scheduleHourlyTaskReminder(this)
        scheduleDailyTaskReminder(this)
    }
}
