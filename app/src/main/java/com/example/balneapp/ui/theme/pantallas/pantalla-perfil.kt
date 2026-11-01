package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.navigation.navigateToBalneAppTab
import com.example.balneapp.ui.viewmodel.PerfilViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel

@Composable
fun PantallaPerfil(
    navController: NavController,
    viewModel: PerfilViewModel = balneAppViewModel()
) {
    val preferencias by viewModel.preferencias.collectAsState()
    var mostrarEdicionPerfil by rememberSaveable { mutableStateOf(false) }

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

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 0) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                navController.navigateToBalneAppTab("principal")

                            }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.ic_balneapp_home),
                                contentDescription = "Inicio",
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

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 1) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { navController.navigateToBalneAppTab("favoritos") }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.ic_balneapp_favorite),
                                contentDescription = "Favoritos",
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
                                painter = painterResource(id = R.drawable.ic_balneapp_explore_logo),
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


                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 3) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { navController.navigateToBalneAppTab("reservas") }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.ic_balneapp_reservation),
                                contentDescription = "Reservas",
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


                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTab == 4) seleccionBgColor else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(R.drawable.ic_balneapp_profile),
                                contentDescription = "Perfil",
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

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { mostrarEdicionPerfil = true }
                ) {
                    Text(
                        text = preferencias.nombre,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = preferencias.email,
                        fontSize = 14.sp,
                        color = Color(0xFF004D5A)
                    )
                    Text(
                        text = "Tocá para editar",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.86f)
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.misdatos),
                            contentDescription = "Mis datos",
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Mis datos",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222)
                        )
                    }
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_balneapp_reservation),
                            contentDescription = "Mis reservas",
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Mis reservas",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222)
                        )
                    }
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_balneapp_favorite),
                            contentDescription = "Favoritos",
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Favoritos",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222)
                        )
                    }
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.metodospago),
                            contentDescription = "Métodos de pago",
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Métodos de pago",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222)
                        )
                    }
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.configuracion),
                            contentDescription = "Configuración",
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Configuración",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222)
                        )
                    }
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ayuda),
                            contentDescription = "Ayuda",
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Ayuda",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222)
                        )
                    }
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cerrarsesion),
                            contentDescription = "Cerrar sesión",
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Cerrar sesión",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = rojoCerrarSesion
                        )
                    }
                }
            }
        }
    }

    if (mostrarEdicionPerfil) {
        var nombreEditado by remember(preferencias.nombre) { mutableStateOf(preferencias.nombre) }
        var emailEditado by remember(preferencias.email) { mutableStateOf(preferencias.email) }

        AlertDialog(
            onDismissRequest = { mostrarEdicionPerfil = false },
            title = { Text("Editar perfil") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = nombreEditado,
                        onValueChange = { nombreEditado = it },
                        label = { Text("Nombre") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = emailEditado,
                        onValueChange = { emailEditado = it },
                        label = { Text("Correo electrónico") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = nombreEditado.isNotBlank() && emailEditado.isNotBlank(),
                    onClick = {
                        viewModel.guardarPerfil(nombreEditado, emailEditado)
                        mostrarEdicionPerfil = false
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarEdicionPerfil = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPerfilPreview() {
    PantallaPerfil(navController = rememberNavController())
}
