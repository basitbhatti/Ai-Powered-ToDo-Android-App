package com.basitbhatti.todoproject

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.basitbhatti.todoproject.presentation.navigation.NavGraph
import com.basitbhatti.todoproject.presentation.theme.ToDoProjectCollaborationTheme
import com.basitbhatti.todoproject.utils.REMINDER_HOUR
import com.basitbhatti.todoproject.utils.REMINDER_MINUTE
import com.basitbhatti.todoproject.utils.broadcasts.ReminderBroadcastReceiver
import com.pdftoexcel.bankstatementconverter.utils.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    lateinit var pref: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = PrefManager(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }


        setContent {
            ToDoProjectCollaborationTheme {
                NavGraph(this@MainActivity, rememberNavController())
            }
        }
    }

    companion object {
        @SuppressLint("ScheduleExactAlarm")
        fun setReminder(context: Context) {




        }
    }


}
