package com.example.balneapp.navigation

import androidx.navigation.NavController

fun NavController.navigateToBalneAppTab(route: String) {
    if (currentDestination?.route == route) return

    navigate(route) {
        popUpTo("principal") { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
