package com.example.balneapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.balneapp.ui.components.BalneAppBottomBar
import com.example.balneapp.ui.theme.pantallas.PantallaAyuda
import com.example.balneapp.ui.theme.pantallas.PantallaConfiguracion
import com.example.balneapp.ui.theme.pantallas.PantallaDisponibilidad
import com.example.balneapp.ui.theme.pantallas.PantallaExplorar
import com.example.balneapp.ui.theme.pantallas.PantallaFavoritos
import com.example.balneapp.ui.theme.pantallas.PantallaGolondrinas
import com.example.balneapp.ui.theme.pantallas.PantallaInicio
import com.example.balneapp.ui.theme.pantallas.PantallaLogueo
import com.example.balneapp.ui.theme.pantallas.PantallaMetodosPago
import com.example.balneapp.ui.theme.pantallas.PantallaMisDatos
import com.example.balneapp.ui.theme.pantallas.PantallaOasis
import com.example.balneapp.ui.theme.pantallas.PantallaPerfil
import com.example.balneapp.ui.theme.pantallas.PantallaPrincipal
import com.example.balneapp.ui.theme.pantallas.PantallaRegistro
import com.example.balneapp.ui.theme.pantallas.PantallaReservas
import com.example.balneapp.ui.theme.pantallas.PantallaResumenReserva
import com.example.balneapp.ui.theme.pantallas.PantallaResenas

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = topLevelDestinations.any { it.route.route == currentRoute }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar && currentRoute != null) {
                BalneAppBottomBar(
                    selectedRoute = currentRoute,
                    onDestinationSelected = navController::navigateToTopLevel
                )
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoute.Splash.route,
            modifier = Modifier.padding(contentPadding)
        ) {
            composable(AppRoute.Splash.route) {
                PantallaInicio(navController = navController)
            }
            composable(AppRoute.Login.route) {
                PantallaLogueo(navController = navController)
            }
            composable(AppRoute.Registro.route) {
                PantallaRegistro(navController = navController)
            }

            composable(AppRoute.Inicio.route) {
                PantallaPrincipal(navController = navController)
            }
            composable(AppRoute.Favoritos.route) {
                PantallaFavoritos(navController = navController)
            }
            composable(AppRoute.Explorar.route) {
                PantallaExplorar(navController = navController)
            }
            composable(AppRoute.Reservas.route) {
                PantallaReservas(navController = navController)
            }
            composable(AppRoute.Perfil.route) {
                PantallaPerfil(navController = navController)
            }

            composable(AppRoute.MisDatos.route) {
                PantallaMisDatos(navController = navController)
            }
            composable(AppRoute.MetodosPago.route) {
                PantallaMetodosPago(navController = navController)
            }
            composable(AppRoute.Configuracion.route) {
                PantallaConfiguracion(navController = navController)
            }
            composable(AppRoute.Ayuda.route) {
                PantallaAyuda(navController = navController)
            }

            composable(
                route = AppRoute.Disponibilidad.route,
                arguments = listOf(
                    navArgument(AppRoute.Disponibilidad.argumentName) { type = NavType.StringType }
                )
            ) { entry ->
                PantallaDisponibilidad(
                    navController = navController,
                    balnearioId = entry.arguments
                        ?.getString(AppRoute.Disponibilidad.argumentName)
                        .orEmpty()
                )
            }
            composable(AppRoute.ResumenReserva.route) {
                PantallaResumenReserva(navController = navController)
            }
            composable(AppRoute.Resenas.route) {
                PantallaResenas(navController = navController)
            }
            composable(AppRoute.Golondrinas.route) {
                PantallaGolondrinas(navController = navController)
            }
            composable(AppRoute.Oasis.route) {
                PantallaOasis(navController = navController)
            }
        }
    }
}
