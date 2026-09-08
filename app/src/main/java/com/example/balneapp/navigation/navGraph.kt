package com.example.balneapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.ui.theme.pantallas.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {

        // splash
        composable("inicio") {
            PantallaInicio(navController = navController)
        }

        // logueo
        composable("logueo") {
            PantallaLogueo(navController = navController)
        }

        //registro
        composable("registro") {
            PantallaRegistro(navController = navController)
        }

        // principal
        composable("principal") {
            PantallaPrincipal(navController = navController)
        }

        //explorar
        composable("explorar") {
            PantallaExplorar(navController = navController)
        }

        //perfil
        composable("perfil") {
            PantallaPerfil(navController = navController)
        }

        //reseñas
        composable("resenas") {
            PantallaResenas(navController = navController)
        }

        //balnearios:
        composable("golondrinas") {
            PantallaGolondrinas(navController = navController)
        }

        composable("oasis") {
            PantallaOasis(navController = navController)
        }
    }
}