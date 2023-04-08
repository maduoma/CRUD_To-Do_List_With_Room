package com.dodemy.roomcrudapp.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dodemy.roomcrudapp.R
import com.dodemy.roomcrudapp.data.entities.Task



fun showTaskReminderNotification(context: Context, task: Task) {
    val channelId = "task_reminder_channel"
    createNotificationChannel(context, channelId)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(task.title)
        .setContentText(task.description)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(task.id, builder.build())
}

private fun createNotificationChannel(context: Context, channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.task_reminder_channel_name)
        val descriptionText = context.getString(R.string.task_reminder_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
