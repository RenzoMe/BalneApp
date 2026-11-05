package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.navigation.AppRoute
import com.example.balneapp.ui.viewmodel.PerfilViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun PantallaInicio(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    LaunchedEffect(Unit) {
        val haySesion = viewModel.sesionIniciada.first { it != null } == true
        delay(1500)
        navController.navigate(if (haySesion) AppRoute.Inicio.route else AppRoute.Login.route) {
            popUpTo(AppRoute.Splash.route) { inclusive = true }
            launchSingleTop = true
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.logocarga),
            contentDescription = "Logo",
            modifier = Modifier
                .size(230.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaInicioPreview() {
    PantallaInicio(navController = rememberNavController())
}
