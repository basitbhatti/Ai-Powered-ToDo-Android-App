package com.basitbhatti.todoproject.utils.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.basitbhatti.todoproject.MainActivity
import com.basitbhatti.todoproject.R
import com.basitbhatti.todoproject.REMINDER_CHANNEL_ID
import com.basitbhatti.todoproject.utils.TASK_ID
import com.basitbhatti.todoproject.utils.TASK_TITLE

class ReminderWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val id = inputData.getInt(TASK_ID, -1)
        val title = inputData.getString(TASK_TITLE)
        if (id != -1) {
            showNotification(id, title)
        }

        return Result.success()
    }

    fun showNotification(id: Int, title: String?) {

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Eating your frog?")
            .setContentText(title ?: "Don't forget to mark it as completed.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setPriority(PRIORITY_MAX)

        val manager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, builder.build())
    }

}