package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.balneapp.R
import com.example.balneapp.navigation.navigateToBalneAppTab
import com.example.balneapp.ui.components.BalneAppBottomBar
import com.example.balneapp.ui.model.BalnearioFavoritoUi
import com.example.balneapp.ui.model.OrdenFavoritos
import com.example.balneapp.ui.viewmodel.FavoritosViewModel
import java.text.NumberFormat
import java.util.Locale

private val fondoFavoritos = Color(0xFFFDF8F4)
private val colorTexto = Color(0xFF171717)
private val colorAgua = Color(0xFF00AFC2)
private val fondoTarjetaFavorito = Color(0xFFCFF7FC)

@Composable
fun PantallaFavoritos(
    navController: NavController,
    viewModel: FavoritosViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var filtroAbierto by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = fondoFavoritos,
        bottomBar = {
            BalneAppBottomBar(
                selectedRoute = "favoritos",
                onDestinationSelected = navController::navigateToBalneAppTab
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(fondoFavoritos)
        ) {
            Image(
                painter = painterResource(R.drawable.decoracion),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 110.dp)
                    .width(88.dp)
                    .height(68.dp)
                    .offset(x = 5.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().height(38.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.balneapp),
                        contentDescription = "BalneApp",
                        modifier = Modifier.width(170.dp).height(34.dp),
                        contentScale = ContentScale.Fit
                    )
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Navega entre tus\nbalnearios favoritos",
                    color = colorTexto,
                    fontSize = 25.sp,
                    lineHeight = 31.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularAction(
                        contentDescription = "Volver al inicio",
                        onClick = { navController.navigateToBalneAppTab("principal") }
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = null, tint = Color(0xFF555555))
                    }

                    Box {
                        CircularAction(
                            contentDescription = "Ordenar favoritos",
                            onClick = { filtroAbierto = true }
                        ) {
                            SliderFilterIcon()
                        }
                        DropdownMenu(
                            expanded = filtroAbierto,
                            onDismissRequest = { filtroAbierto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Recomendados") },
                                onClick = {
                                    viewModel.ordenarPor(OrdenFavoritos.RECOMENDADOS)
                                    filtroAbierto = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Mejor puntuación") },
                                onClick = {
                                    viewModel.ordenarPor(OrdenFavoritos.MEJOR_PUNTUACION)
                                    filtroAbierto = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Menor precio") },
                                onClick = {
                                    viewModel.ordenarPor(OrdenFavoritos.MENOR_PRECIO)
                                    filtroAbierto = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF263B68),
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Cerca de ti", fontSize = 18.sp, color = Color(0xFF555555))
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.favoritos.isEmpty()) {
                    Text(
                        text = "Todavía no guardaste balnearios favoritos.",
                        color = Color(0xFF666666),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        uiState.favoritos.forEach { favorito ->
                            TarjetaFavorito(
                                favorito = favorito,
                                modifier = Modifier.weight(1f),
                                onQuitar = { viewModel.quitarFavorito(favorito.id) },
                                onAbrir = { route -> navController.navigate(route) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CircularAction(
    contentDescription: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD8D8D8))
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
private fun SliderFilterIcon() {
    Canvas(modifier = Modifier.size(22.dp)) {
        val strokeWidth = 1.7.dp.toPx()
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        val tracks = listOf(size.width * 0.26f, size.width * 0.5f, size.width * 0.74f)
        tracks.forEachIndexed { index, x ->
            drawLine(
                color = Color(0xFF555555),
                start = Offset(x, size.height * 0.16f),
                end = Offset(x, size.height * 0.84f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            val knobY = when (index) {
                0 -> size.height * 0.42f
                1 -> size.height * 0.64f
                else -> size.height * 0.34f
            }
            drawCircle(Color.White, radius = 2.6.dp.toPx(), center = Offset(x, knobY))
            drawCircle(Color(0xFF555555), radius = 2.6.dp.toPx(), center = Offset(x, knobY), style = stroke)
        }
    }
}

@Composable
private fun TarjetaFavorito(
    favorito: BalnearioFavoritoUi,
    modifier: Modifier = Modifier,
    onQuitar: () -> Unit,
    onAbrir: (String) -> Unit
) {
    val openModifier = favorito.rutaDetalle?.let { route ->
        Modifier.clickable { onAbrir(route) }
    } ?: Modifier

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(fondoTarjetaFavorito)
            .then(openModifier)
    ) {
        Box {
            Image(
                painter = painterResource(favorito.imagenResId),
                contentDescription = favorito.nombre,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = onQuitar,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(28.dp)
                    .background(Color.White.copy(alpha = 0.92f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Quitar de favoritos",
                    tint = Color(0xFF72C6D1),
                    modifier = Modifier.size(17.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(fondoTarjetaFavorito)
                .padding(horizontal = 7.dp, vertical = 5.dp)
        ) {
            Text(
                text = favorito.nombre,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF606060),
                    modifier = Modifier.size(11.dp)
                )
                Text(
                    text = favorito.direccion,
                    color = Color(0xFF8A8A8A),
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Spacer(modifier = Modifier.weight(1f))
                Text("Desde", fontSize = 7.sp, fontWeight = FontWeight.Bold, color = Color(0xFF555555))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "${String.format(Locale("es", "AR"), "%.1f", favorito.puntuacion)} ",
                        color = Color(0xFF0D7989),
                        fontSize = 10.sp
                    )
                    Text(
                        text = "(${favorito.cantidadResenas})",
                        color = Color(0xFF9E9E9E),
                        fontSize = 9.sp
                    )
                }
                Text(
                    text = "$ " + NumberFormat.getIntegerInstance(Locale("es", "AR")).format(favorito.precioDesde),
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
}
