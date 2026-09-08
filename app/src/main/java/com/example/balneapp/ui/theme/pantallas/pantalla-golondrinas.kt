package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun PantallaGolondrinas(navController: NavController) {

    val fondoCrema = Color(0xFFFFFBF2)
    val celesteInsignia = Color(0xFFD0F8FF)
    val celesteTexto = Color(0xFF00A3C4)
    val celesteBoton = Color(0xFF00B5D8)
    val textoOscuro = Color(0xFF222222)
    val textoGris = Color(0xFF757575)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoCrema)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                // Imagen principal
                Image(
                    painter = painterResource(id = R.drawable.imagengolondrina),
                    contentDescription = "Balneario Golondrinas",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Boton volver hacia atras
                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(start = 16.dp, top = 8.dp)
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

                //Boton favorito
                Surface(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(end = 16.dp, top = 8.dp)
                        .size(42.dp)
                        .align(Alignment.TopEnd),
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = Color(0xFF00B5D8)
                        )
                    }
                }

                // Mini imagenes del balneario
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 28.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Miniimagen1
                    Image(
                        painter = painterResource(id = R.drawable.imagengolondrina),
                        contentDescription = "Miniatura 1",
                        modifier = Modifier
                            .size(width = 65.dp, height = 50.dp)
                            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Miniimagen2
                    Image(
                        painter = painterResource(id = R.drawable.imagenpaco),
                        contentDescription = "Miniatura 2",
                        modifier = Modifier
                            .size(width = 65.dp, height = 50.dp)
                            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Miniimagen3
                    Image(
                        painter = painterResource(id = R.drawable.img_oasis),
                        contentDescription = "Miniatura 3",
                        modifier = Modifier
                            .size(width = 65.dp, height = 50.dp)
                            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = fondoCrema
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    // Título Balneario Golondrinas
                    Text(
                        text = "Balneario Golondrinas",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = textoOscuro
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Ubicacipn
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = textoOscuro,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Av. costanera y 33",
                            fontSize = 14.sp,
                            color = textoOscuro
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = celesteInsignia
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Estrella",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "4.8",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = celesteTexto
                            )
                            Text(
                                text = "(185)",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            //boton de reseñas
                            Row(
                                modifier = Modifier.clickable {
                                    navController.navigate("resenas")
                                },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Ver Reseñas",
                                    tint = celesteTexto,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Reseñas",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = celesteTexto
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Subtítulo de los servicios
                    Text(
                        text = "Servicios",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = celesteTexto
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    //Fila y sus logos/img
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Carpas
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.icono_carpa),
                                contentDescription = "Carpas",
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Carpas",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = textoOscuro
                            )
                        }

                        // WiFi
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.icono_wifi),
                                contentDescription = "WiFi",
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "WiFi",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = textoOscuro
                            )
                        }

                        // Duchas
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.icono_duchas),
                                contentDescription = "Duchas",
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Duchas",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = textoOscuro
                            )
                        }

                        // Quincho
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.icono_quincho),
                                contentDescription = "Quincho",
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Quincho",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = textoOscuro
                            )
                        }

                        // Accesibilidad
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.icono_accesibilidad),
                                contentDescription = "Accesibilidad",
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Accesibilidad",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = textoOscuro
                            )
                        }

                        // Pet Friendly
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.icono_mascotas),
                                contentDescription = "Pet Friendly",
                                modifier = Modifier.size(38.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Pet Friendly",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = textoOscuro
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    //Contacto
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contacto ",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textoOscuro
                        )
                        Text(
                            text = "·2291-420115",
                            fontSize = 15.sp,
                            color = textoGris
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    //Descripcion
                    Text(
                        text = "Descripción",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textoOscuro
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Un balneario tranquilo e ideal para la familia, con vista privilegiada al mar, carpas confortables, servicios gastronómicos y espacio recreativo.",
                        fontSize = 16.sp,
                        color = textoGris,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    //BARRA INFERIOR (precio y boton)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Desde",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = textoGris
                            )
                            Text(
                                text = "$ 28.000",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Button(
                            onClick = { /* Sin funcionalidad actualmente */ },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = celesteBoton),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = "Ver disponibilidad",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaGolondrinasPreview() {
    PantallaGolondrinas(navController = rememberNavController())
}