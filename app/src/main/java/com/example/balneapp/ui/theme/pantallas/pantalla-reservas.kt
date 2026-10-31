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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.balneapp.R
import com.example.balneapp.navigation.navigateToBalneAppTab
import com.example.balneapp.ui.components.BalneAppBottomBar
import com.example.balneapp.ui.components.CalendarioReserva
import com.example.balneapp.ui.model.EquipamientoReserva
import com.example.balneapp.ui.model.ReservasUiState
import com.example.balneapp.ui.viewmodel.ReservasViewModel

private val colorTurquesa = Color(0xFF68D2DE)
private val fondoReserva = Color(0xFFF0F0F0)
private val colorBotonReserva = Color(0xFF24AEBB)
private val colorEquipamiento = Color(0xFFB7EDF7)

@Composable
fun PantallaReservas(
    navController: NavController,
    viewModel: ReservasViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = androidx.compose.runtime.remember { SnackbarHostState() }

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        containerColor = fondoReserva,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BalneAppBottomBar(
                selectedRoute = "reservas",
                onDestinationSelected = navController::navigateToBalneAppTab
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(fondoReserva)
        ) {
            EncabezadoReserva(onVolver = {
                if (!navController.popBackStack()) navController.navigate("principal")
            })

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp)
                ) {
                    Spacer(modifier = Modifier.height(14.dp))
                    CalendarioReserva(
                        mes = uiState.mes,
                        diasSeleccionados = uiState.diasSeleccionados,
                        mostrarCalendarioInteractivo = uiState.mostrarCalendarioInteractivo,
                        onMesAnterior = viewModel::mesAnterior,
                        onMesSiguiente = viewModel::mesSiguiente,
                        onSeleccionarDia = viewModel::seleccionarDia,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 22.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 28.dp),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        EquipamientoChip(
                            label = EquipamientoReserva.CARPAS.etiqueta,
                            selected = EquipamientoReserva.CARPAS in uiState.equipamiento,
                            onClick = { viewModel.alternarEquipamiento(EquipamientoReserva.CARPAS) },
                            modifier = Modifier.weight(1f)
                        )
                        EquipamientoChip(
                            label = EquipamientoReserva.SOMBRILLAS.etiqueta,
                            selected = EquipamientoReserva.SOMBRILLAS in uiState.equipamiento,
                            onClick = { viewModel.alternarEquipamiento(EquipamientoReserva.SOMBRILLAS) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))
                    SelectorPersonas(
                        cantidad = uiState.personas,
                        onDisminuir = { viewModel.cambiarPersonas(-1) },
                        onAumentar = { viewModel.cambiarPersonas(1) }
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    FilaMonto(total = uiState.total)
                    Spacer(modifier = Modifier.height(96.dp))
                }

                Button(
                    onClick = viewModel::confirmarReserva,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(0.88f)
                        .padding(bottom = 22.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorBotonReserva),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Confirmar y Pagar",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
private fun EncabezadoReserva(onVolver: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(colorTurquesa)
    ) {
        IconButton(
            onClick = onVolver,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 14.dp)
                .size(40.dp)
                .background(Color.White, CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF28AEBB),
                modifier = Modifier.size(22.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.reserva_logo),
            contentDescription = "BalneApp",
            modifier = Modifier
                .align(Alignment.Center)
                .width(190.dp)
                .height(42.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun EquipamientoChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(40.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        color = if (selected) colorEquipamiento else Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun SelectorPersonas(
    cantidad: Int,
    onDisminuir: () -> Unit,
    onAumentar: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth().height(64.dp)) {
        Image(
            painter = painterResource(R.drawable.reserva_personas),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 68.dp)
                .width(142.dp)
                .height(48.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = cantidad.toString() + " Personas",
                color = Color(0xFF111111),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 65.dp)
                .size(width = 45.dp, height = 48.dp)
                .clickable(onClick = onDisminuir)
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 4.dp)
                .size(width = 45.dp, height = 48.dp)
                .clickable(onClick = onAumentar)
        )
    }
}

@Composable
private fun FilaMonto(total: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.reserva_dinero),
            contentDescription = "Monto de reserva",
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(28.dp))
        Text(
            text = "Monto:",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$" + total,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(end = 38.dp)
        )
    }
}
