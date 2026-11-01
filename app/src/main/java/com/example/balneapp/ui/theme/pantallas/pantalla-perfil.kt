package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.navigation.AppRoute
import com.example.balneapp.navigation.navigateSingleTop
import com.example.balneapp.navigation.navigateToTopLevel
import com.example.balneapp.ui.viewmodel.PerfilViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel

private val fondoPerfil = Color.White
private val turquesaPerfil = Color(0xFF3BB3C6)
private val textoPerfil = Color(0xFF292929)

@Composable
fun PantallaPerfil(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    val preferencias by viewModel.preferencias.collectAsState()
    var confirmarCierre by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = fondoPerfil
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(fondoPerfil)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(turquesaPerfil)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 22.dp, top = 20.dp, bottom = 16.dp)
                        .clickable { navController.navigateSingleTop(AppRoute.MisDatos) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.rodrigo1),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = preferencias.nombre.ifBlank { "Tu perfil" },
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = preferencias.email,
                            color = Color(0xFF004D5A),
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Tocá para editar",
                            color = Color.White.copy(alpha = 0.86f),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(top = 140.dp),
                shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 22.dp, vertical = 12.dp)
                ) {
                    OpcionPerfil(R.drawable.misdatos, "Mis datos") {
                        navController.navigateSingleTop(AppRoute.MisDatos)
                    }
                    OpcionPerfil(R.drawable.ic_balneapp_reservation, "Mis reservas") {
                        navController.navigateToTopLevel(AppRoute.Reservas)
                    }
                    OpcionPerfil(R.drawable.ic_balneapp_favorite, "Favoritos") {
                        navController.navigateToTopLevel(AppRoute.Favoritos)
                    }
                    OpcionPerfil(R.drawable.metodospago, "Métodos de pago") {
                        navController.navigateSingleTop(AppRoute.MetodosPago)
                    }
                    OpcionPerfil(R.drawable.configuracion, "Configuración") {
                        navController.navigateSingleTop(AppRoute.Configuracion)
                    }
                    OpcionPerfil(R.drawable.ayuda, "Ayuda") {
                        navController.navigateSingleTop(AppRoute.Ayuda)
                    }
                    OpcionPerfil(R.drawable.cerrarsesion, "Cerrar sesión", destructiva = true) {
                        confirmarCierre = true
                    }
                }
            }
        }
    }

    if (confirmarCierre) {
        AlertDialog(
            onDismissRequest = { confirmarCierre = false },
            title = { Text("¿Cerrar sesión?") },
            text = { Text("Vas a tener que iniciar sesión nuevamente para volver a tu perfil.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmarCierre = false
                        viewModel.cerrarSesion {
                            navController.navigate(AppRoute.Login.route) {
                                popUpTo(AppRoute.Inicio.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                ) { Text("Cerrar sesión", color = Color(0xFFD84C4C)) }
            },
            dismissButton = {
                TextButton(onClick = { confirmarCierre = false }) { Text("Cancelar", color = turquesaPerfil) }
            },
            containerColor = Color.White
        )
    }
}

@Composable
private fun OpcionPerfil(
    icono: Int,
    titulo: String,
    destructiva: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (destructiva) Color(0xFFEB6B6B) else textoPerfil
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icono),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = titulo,
                modifier = Modifier.weight(1f),
                color = color,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        if (!destructiva) HorizontalDivider(color = Color(0xFFECECEC), thickness = 0.7.dp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPerfilPreview() {
    PantallaPerfil(navController = rememberNavController())
}
