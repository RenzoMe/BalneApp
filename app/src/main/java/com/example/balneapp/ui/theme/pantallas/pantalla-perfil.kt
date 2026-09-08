package com.example.balneapp.ui.theme.pantallas

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R

@Composable
fun PantallaPerfil(navController: NavController) {
    val grisBarra = Color(0xFFFAFAFA)
    val seleccionBgColor = Color(0xFFCFF5FC)
    val turquesaHeader = Color(0xFF3BB3C6)
    val rojoCerrarSesion = Color(0xFFEB6B6B)
    val celesteAgua = Color(0xFF00A3C4)

    val selectedTab = 4

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                color = grisBarra,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Inicio
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 0) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                navController.navigate("principal")

                            }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Inicio",
                                tint = Color.Gray,
                                modifier = Modifier.size(26.dp)
                            )
                            Text(
                                text = "Inicio",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    }

                    // Favoritos
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 1) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favoritos",
                                tint = Color.Gray,
                                modifier = Modifier.size(26.dp)
                            )
                            Text(
                                text = "Favoritos",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    }

                    // Explorar
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 2) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                navController.navigate("explorar")
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Explorar",
                                modifier = Modifier.size(26.dp)
                            )
                            Text(
                                text = "Explorar",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    }

                    // Reservas
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 3) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Reservas",
                                tint = Color.Gray,
                                modifier = Modifier.size(26.dp)
                            )
                            Text(
                                text = "Reservas",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    }

                    // Perfil
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 4) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Perfil",
                                tint = celesteAgua,
                                modifier = Modifier.size(26.dp)
                            )
                            Text(
                                text = "Perfil",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = celesteAgua
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(turquesaHeader)
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rodrigo1),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Rodrigo Perez",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "rodrigoperez@gmail.com",
                        fontSize = 14.sp,
                        color = Color(0xFF004D5A)
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OpcionPerfilItem(iconRes = R.drawable.misdatos, titulo = "Mis datos")
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                    OpcionPerfilItem(iconRes = R.drawable.misreservas, titulo = "Mis reservas")
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                    OpcionPerfilItem(iconRes = R.drawable.favoritos, titulo = "Favoritos")
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                    OpcionPerfilItem(iconRes = R.drawable.metodospago, titulo = "Métodos de pago")
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                    OpcionPerfilItem(iconRes = R.drawable.configuracion, titulo = "Configuración")
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                    OpcionPerfilItem(iconRes = R.drawable.ayuda, titulo = "Ayuda")
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                    OpcionPerfilItem(
                        iconRes = R.drawable.cerrarsesion,
                        titulo = "Cerrar sesión",
                        colorTexto = rojoCerrarSesion,
                    )
                }
            }
        }
    }
}

@Composable
fun OpcionPerfilItem(
    @DrawableRes iconRes: Int,
    titulo: String,
    colorTexto: Color = Color(0xFF222222),
    colorIcono: Color? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = titulo,
            modifier = Modifier.size(26.dp),
            colorFilter = colorIcono?.let { ColorFilter.tint(it) }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = titulo,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = colorTexto
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPerfilPreview() {
    PantallaPerfil(navController = rememberNavController())
}