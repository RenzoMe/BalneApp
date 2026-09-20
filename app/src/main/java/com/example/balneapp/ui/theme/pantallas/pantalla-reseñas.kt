package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun PantallaResenas(navController: NavController) {

    val fondoGris = Color(0xFFF3F3F3)
    val celesteCabecera = Color(0xFF6EDCEB)
    val celesteChip = Color(0xFFD0F8FF)
    val textoGris = Color(0xFF757575)
    val moradoInteraccion = Color(0xFF3F3D56)

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0), // Evita que Scaffold meta margen blanco arriba

        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            navController.navigate("principal")
                        }
                    ) {
                        Icon(Icons.Default.Home, contentDescription = "Inicio", tint = textoGris)
                        Text("Inicio", fontSize = 11.sp, color = textoGris)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Sin navegación por ahora */ }
                    ) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favoritos", tint = textoGris)
                        Text("Favoritos", fontSize = 11.sp, color = textoGris)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { navController.navigate("explorar") }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Explorar",
                            modifier = Modifier.size(24.dp)
                        )
                        Text("Explorar", fontSize = 11.sp, color = textoGris)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Sin navegación por ahora */ }
                    ) {
                        Icon(Icons.Default.DateRange, contentDescription = "Reservas", tint = textoGris)
                        Text("Reservas", fontSize = 11.sp, color = textoGris)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { navController.navigate("perfil") }
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = textoGris)
                        Text("Perfil", fontSize = 11.sp, color = textoGris)
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoGris)
                .padding(bottom = paddingValues.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(celesteCabecera)
                    .statusBarsPadding()
                    .padding(start = 16.dp, top = 8.dp, bottom = 12.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .size(42.dp)
                        .clickable { navController.popBackStack() },
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF3BB3C6)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.estadisticas),
                        contentDescription = "Estadísticas de Reseñas",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FiltroChipItem("Todas", celesteChip)
                    FiltroChipItem("Mejores", celesteChip)
                    FiltroChipItem("Peores", celesteChip)
                    FiltroChipItem("Con fotos", celesteChip)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TarjetaResenaItem(
                    fotoRes = R.drawable.imagenpaco,
                    nombre = "María Blanco",
                    fecha = "15 Mayo 2026",
                    estrellas = 3,
                    comentario = "Muy linda experiencia en familia. La atención fue muy buena y la comida muy rica. Volveríamos el año que viene. Como único punto a mejorar, el baño no estaba en buenas condiciones al llegar.",
                    utilesCount = 15,
                    moradoColor = moradoInteraccion
                )

                Spacer(modifier = Modifier.height(14.dp))

                TarjetaResenaItem(
                    fotoRes = R.drawable.imagenpaco,
                    nombre = "Franco Colapinto",
                    fecha = "27 Mayo 2026",
                    estrellas = 4,
                    comentario = "Fue una buena experiencia la volveria a repetir con mi familia, los baños estaban sucios pero nada grabe.",
                    utilesCount = 999,
                    moradoColor = moradoInteraccion
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun FiltroChipItem(texto: String, fondoColor: Color) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = fondoColor
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF222222)
        )
    }
}

@Composable
private fun TarjetaResenaItem(
    fotoRes: Int,
    nombre: String,
    fecha: String,
    estrellas: Int,
    comentario: String,
    utilesCount: Int,
    moradoColor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = fotoRes),
                        contentDescription = nombre,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = nombre,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222)
                    )
                }
                Text(
                    text = "· $fecha",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            
            Row {
                repeat(estrellas) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrella",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = comentario,
                fontSize = 13.sp,
                color = Color(0xFF444444),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* Sin funcionalidad actualmente */ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ThumbUp,
                        contentDescription = "Útil",
                        tint = moradoColor,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Útil ($utilesCount)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = moradoColor
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Responder",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = moradoColor,
                    modifier = Modifier.clickable { /* Sin funcionalidad actualmente */ }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaResenasPreview() {
    PantallaResenas(navController = rememberNavController())
}