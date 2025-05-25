package com.basitbhatti.todoproject.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.basitbhatti.todoproject.presentation.viewmodel.TaskViewModel
import com.basitbhatti.todoproject.presentation.ui.screens.HomeScreen
import com.basitbhatti.todoproject.presentation.ui.screens.UserTypeScreen

@Composable
fun NavGraph(
    context: Context,
    navController: NavHostController,
    viewModel: TaskViewModel = hiltViewModel(),
) {

    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(viewModel = viewModel, navController)
        }

        composable(
            route = Screen.UserType.route
        ) {
            UserTypeScreen(context = context) {
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.UserType.route){
                        inclusive = true
                    }
                }
            }
        }


    }

}