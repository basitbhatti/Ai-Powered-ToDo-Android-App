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


    fun createNotificationChannel() {
        val name = "Reminder Channel"
        val descriptionText = "This channel sends task reminder notification."
        val importance = NotificationManager.IMPORTANCE_HIGH

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(REMINDER_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        manager.createNotificationChannel(channel)
    }


}