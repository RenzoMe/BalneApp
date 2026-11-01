package com.example.balneapp.navigation

import androidx.navigation.NavController

/** Opens a destination once when profile options are tapped repeatedly. */
fun NavController.navigateSingleTop(destination: AppRoute) {
    if (currentDestination?.route == destination.route) return

    navigate(destination.route) {
        launchSingleTop = true
    }
}
