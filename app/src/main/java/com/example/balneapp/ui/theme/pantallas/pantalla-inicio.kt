package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import kotlinx.coroutines.delay
// funcion de navegacion con espera de 2 segundos, ir a pantalla logueo
@Composable
fun PantallaInicio(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("logueo") {
            popUpTo("inicio") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate("logueo") {
                    popUpTo("inicio") { inclusive = true }
                }
            }
    ) {
        //imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
            //imagen de logo balneapp
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