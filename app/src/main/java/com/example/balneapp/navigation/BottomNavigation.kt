package com.example.balneapp.navigation

import androidx.navigation.NavController
import com.example.balneapp.R

data class TopLevelDestination(
    val route: AppRoute,
    val label: String,
    val iconRes: Int
)

val topLevelDestinations = listOf(
    TopLevelDestination(AppRoute.Inicio, "Inicio", R.drawable.ic_balneapp_home),
    TopLevelDestination(AppRoute.Favoritos, "Favoritos", R.drawable.ic_balneapp_favorite),
    TopLevelDestination(AppRoute.Explorar, "Explorar", R.drawable.ic_balneapp_explore_logo),
    TopLevelDestination(AppRoute.Reservas, "Reservas", R.drawable.ic_balneapp_reservation),
    TopLevelDestination(AppRoute.Perfil, "Perfil", R.drawable.ic_balneapp_profile)
)

/** Navigate between the five primary tabs without growing the back stack. */
fun NavController.navigateToTopLevel(destination: AppRoute) {
    if (currentDestination?.route == destination.route) return

    navigate(destination.route) {
        popUpTo(AppRoute.Inicio.route) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
