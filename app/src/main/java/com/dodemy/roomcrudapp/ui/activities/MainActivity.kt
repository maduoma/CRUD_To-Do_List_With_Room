package com.dodemy.roomcrudapp.ui.activities


import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import java.util.Locale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dodemy.roomcrudapp.R
import dagger.hilt.android.AndroidEntryPoint
import com.dodemy.roomcrudapp.databinding.ActivityMainBinding
import com.dodemy.roomcrudapp.workers.TaskReminderWorker
import com.dodemy.roomcrudapp.workers.scheduleTaskReminder
import com.dodemy.roomcrudapp.workers.scheduleTaskReminderEvery10Seconds
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnInitListener  {
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contentBinding = ActivityMainBinding.bind(binding.root)

        // Set support ActionBar
        setSupportActionBar(contentBinding.toolbar)

        // Set up navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)
//        scheduleHourlyTaskReminder(this)
//        scheduleDailyTaskReminder(this)
//        scheduleTaskReminder(this)
        scheduleTaskReminderEvery10Seconds(this)
        textToSpeech = TextToSpeech(this, this)

        // Call scheduleTaskReminderWorker
//        scheduleTaskReminderWorker()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun scheduleTaskReminderWorker() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "task_reminder_worker",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This language is not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

}
