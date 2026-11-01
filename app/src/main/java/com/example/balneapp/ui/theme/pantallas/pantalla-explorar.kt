package com.example.balneapp.ui.theme.pantallas

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.ui.components.BotonFavoritoBalneario
import com.example.balneapp.ui.model.BalnearioIds
import com.example.balneapp.ui.viewmodel.ExplorarViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel
import java.util.Locale

@Composable
fun PantallaExplorar(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    val viewModel: ExplorarViewModel = balneAppViewModel()
    val ubicacionState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val activity = context.findActivity()
    var mostrarExplicacionUbicacion by rememberSaveable { mutableStateOf(false) }
    var permisoUbicacionDenegado by rememberSaveable { mutableStateOf(false) }
    val permisoUbicacion = Manifest.permission.ACCESS_COARSE_LOCATION

    val launcherPermisoUbicacion = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            permisoUbicacionDenegado = false
            viewModel.cargarUbicacion()
        } else {
            permisoUbicacionDenegado = true
            viewModel.mostrarPermisoDenegado()
        }
    }

    val usarUbicacion = {
        val yaConcedido = ContextCompat.checkSelfPermission(
            context,
            permisoUbicacion
        ) == PackageManager.PERMISSION_GRANTED

        when {
            yaConcedido -> viewModel.cargarUbicacion()
            permisoUbicacionDenegado && activity != null &&
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permisoUbicacion) -> {
                context.startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                )
            }
            else -> mostrarExplicacionUbicacion = true
        }
    }
    val azulOscuro = Color(0xFF0D075E)
    val celesteBoton = Color(0xFF00B5D8)
    val celesteAgua = Color(0xFF00A3C4)

    Scaffold { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.mapafondo),
                contentDescription = "Mapa de Explorar",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color.Gray
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(22.dp),
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                decorationBox = { innerTextField ->
                                    if (searchText.isEmpty()) {
                                        Text(
                                            text = "Buscar Balnearios",
                                            color = Color.Gray,
                                            fontSize = 14.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = Color.Gray
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .size(44.dp)
                            .clickable(onClick = usarUbicacion),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (ubicacionState.estaCargando) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            } else {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Usar mi ubicación aproximada",
                                    tint = if (ubicacionState.ubicacion != null) celesteAgua else Color.Gray
                                )
                            }
                        }
                    }
                }

                val textoUbicacion = when {
                    ubicacionState.estaCargando -> "Buscando ubicación aproximada…"
                    ubicacionState.ubicacion != null -> {
                        val ubicacion = requireNotNull(ubicacionState.ubicacion)
                        "Ubicación aproximada: ${String.format(Locale("es", "AR"), "%.2f", ubicacion.latitud)}, " +
                            String.format(Locale("es", "AR"), "%.2f", ubicacion.longitud)
                    }
                    else -> ubicacionState.mensaje
                }
                textoUbicacion?.let { mensaje ->
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White.copy(alpha = 0.96f),
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            text = mensaje,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            color = Color(0xFF444444),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = innerPadding.calculateBottomPadding() + 10.dp
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.imagengolondrina),
                        contentDescription = "Balneario Horizonte",
                        modifier = Modifier
                            .size(width = 110.dp, height = 120.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Balneario Horizonte",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = azulOscuro
                            )
                            BotonFavoritoBalneario(
                                balnearioId = BalnearioIds.HORIZONTE,
                                tamanoBoton = 32.dp,
                                tamanoIcono = 20.dp,
                                colorFondo = Color.Transparent
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(R.drawable.ic_balneapp_review),
                                    contentDescription = "Puntuación",
                                    modifier = Modifier.size(14.dp),
                                    colorFilter = ColorFilter.tint(Color(0xFFFFC107))
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "4.8 (320)",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Distancia",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "1.2 km",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Desde ",
                                fontSize = 12.sp,
                                color = azulOscuro
                            )
                            Text(
                                text = "$25.000",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = azulOscuro
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { /* Sin funcionalidad */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = celesteBoton),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Ver detalles",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    if (mostrarExplicacionUbicacion) {
        AlertDialog(
            onDismissRequest = { mostrarExplicacionUbicacion = false },
            title = { Text("Usar ubicación aproximada") },
            text = {
                Text("BalneApp necesita este permiso para mostrar tu posición aproximada en Explorar. La ubicación no se guarda.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarExplicacionUbicacion = false
                        launcherPermisoUbicacion.launch(permisoUbicacion)
                    }
                ) {
                    Text("Continuar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarExplicacionUbicacion = false }) {
                    Text("Ahora no")
                }
            }
        )
    }
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaExplorarPreview() {
    PantallaExplorar(navController = rememberNavController())
}
