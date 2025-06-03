package com.basitbhatti.todoproject.utils.broadcasts

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.basitbhatti.todoproject.DAILY_REMINDER_CHANNEL
import com.basitbhatti.todoproject.MainActivity
import com.basitbhatti.todoproject.MainActivity.Companion.setReminder
import com.basitbhatti.todoproject.R

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0!=null){
            showNotification(p0)
            setReminder(p0!!)
        }
    }

    private fun showNotification(context: Context?) {
        if (context != null) {

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, DAILY_REMINDER_CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Schedule Your Tomorrow.")
                .setContentText("Be proactive and plan tomorrow!")
                .setPriority(NotificationCompat.PRIORITY_MAX).setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(212, notification.build())


        }

    }
}