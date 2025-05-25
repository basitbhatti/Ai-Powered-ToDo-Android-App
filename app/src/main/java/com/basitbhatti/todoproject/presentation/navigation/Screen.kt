package com.basitbhatti.todoproject.presentation.navigation

sealed class Screen(val route: String, val title: String? = null) {

    object Home : Screen("home", "Home")
    object UserType : Screen("user_type", "Home")


}