package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.balneapp.R
import com.example.balneapp.navigation.navigateToBalneAppTab
import com.example.balneapp.ui.components.BalneAppBottomBar
import com.example.balneapp.ui.components.BotonFavoritoBalneario
import com.example.balneapp.ui.model.BalnearioIds
import com.example.balneapp.ui.viewmodel.PerfilViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel

@Composable
fun PantallaPrincipal(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    val perfilViewModel: PerfilViewModel = balneAppViewModel()
    val preferencias by perfilViewModel.preferencias.collectAsState()

    val azulOscuro = Color(0xFF0D075E)
    val celesteAgua = Color(0xFF00A3C4)
    val cardBgColor = Color(0xFFF0FDFF)

    Scaffold(
        bottomBar = {
            BalneAppBottomBar(
                selectedRoute = "principal",
                onDestinationSelected = navController::navigateToBalneAppTab
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.decoracion),
                contentDescription = "Decoración",
                modifier = Modifier
                    .height(130.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 20.dp),
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.balneapp),
                    contentDescription = "BalneApp",
                    modifier = Modifier.height(40.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Hola, ${preferencias.nombre.substringBefore(' ')}!",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Text(
                    text = "Encontrá tu lugar\nideal para relajarte",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulOscuro,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Buscar Balneario") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ubicación",
                        tint = azulOscuro,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Cerca de ti",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate("golondrinas") },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBgColor)
                    ) {
                        Column {
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.imagengolondrina),
                                    contentDescription = "Balneario Golondrinas",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp),
                                    contentScale = ContentScale.Crop
                                )
                                BotonFavoritoBalneario(
                                    balnearioId = BalnearioIds.GOLONDRINAS,
                                    modifier = Modifier.padding(6.dp).align(Alignment.TopEnd),
                                    tamanoBoton = 30.dp,
                                    tamanoIcono = 16.dp,
                                    colorActivo = celesteAgua
                                )
                            }

                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "Balneario Golondrinas",
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Dirección",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "Av. costanera y 27",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_balneapp_review),
                                            contentDescription = "Puntuación",
                                            modifier = Modifier.size(12.dp),
                                            colorFilter = ColorFilter.tint(Color(0xFFFFD700))
                                        )
                                        Text(
                                            text = "4,4 ",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = celesteAgua
                                        )
                                        Text(
                                            text = "(134)",
                                            fontSize = 9.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "Desde",
                                            fontSize = 8.sp,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = "$ 15.000",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = azulOscuro
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate("oasis") },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBgColor)
                    ) {
                        Column {
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.imagenpaco),
                                    contentDescription = "Balneario OASIS",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp),
                                    contentScale = ContentScale.Crop
                                )
                                BotonFavoritoBalneario(
                                    balnearioId = BalnearioIds.OASIS,
                                    modifier = Modifier.padding(6.dp).align(Alignment.TopEnd),
                                    tamanoBoton = 30.dp,
                                    tamanoIcono = 16.dp,
                                    colorActivo = celesteAgua
                                )
                            }

                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "Balneario OASIS",
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Dirección",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "Av. costanera y 29",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_balneapp_review),
                                            contentDescription = "Puntuación",
                                            modifier = Modifier.size(12.dp),
                                            colorFilter = ColorFilter.tint(Color(0xFFFFD700))
                                        )
                                        Text(
                                            text = "3,8 ",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = celesteAgua
                                        )
                                        Text(
                                            text = "(103)",
                                            fontSize = 9.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "Desde",
                                            fontSize = 8.sp,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = "$ 11.000",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = azulOscuro
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPrincipalPreview() {
    PantallaPrincipal(navController = rememberNavController())
}
