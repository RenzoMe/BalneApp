package com.example.balneapp.navigation

import androidx.navigation.NavController

fun NavController.navigateToBalneAppTab(route: String) {
    if (currentDestination?.route == route) return

    navigate(route) {
        popUpTo("principal") { inclusive = false }
        launchSingleTop = true
    }
}
