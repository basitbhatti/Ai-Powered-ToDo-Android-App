package com.basitbhatti.todoproject.worker

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.basitbhatti.todoproject.R
import com.basitbhatti.todoproject.REMINDER_CHANNEL_ID
import com.basitbhatti.todoproject.domain.model.TaskItemEntity

class ReminderWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        task?.let {
            sendNotification(task)
        }
        return Result.success()
    }

    private fun sendNotification(task: TaskItemEntity?) {
        val manager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(applicationContext, REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(task?.title)
            .setContentText("Working on this high-priority task?")
            .setPriority(Notification.PRIORITY_HIGH)

        manager.notify(task?.id?:1, builder.build())



    }

}