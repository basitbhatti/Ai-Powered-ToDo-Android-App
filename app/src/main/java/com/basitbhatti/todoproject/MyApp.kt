package com.basitbhatti.todoproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

const val REMINDER_CHANNEL_ID = "REMINDER_CHANNEL"

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channelName = "Reminder Channel"
        val descriptionText = "This channel is to remind users about the high priority tasks."
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(REMINDER_CHANNEL_ID, channelName, importance).apply {
            description = descriptionText
        }

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }


}