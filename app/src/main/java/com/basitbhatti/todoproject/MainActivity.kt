package com.basitbhatti.todoproject

import android.Manifest
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
import com.basitbhatti.todoproject.presentation.ui.screens.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }


        setContent {
            ToDoProjectCollaborationTheme {
                NavGraph(this@MainActivity, rememberNavController())
            }
        }
    }
}
