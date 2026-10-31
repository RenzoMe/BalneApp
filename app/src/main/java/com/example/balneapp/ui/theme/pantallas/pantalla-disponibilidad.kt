package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.balneapp.R
import com.example.balneapp.ui.components.CalendarioDisponibilidad
import com.example.balneapp.ui.model.OpcionEquipamientoReserva
import com.example.balneapp.ui.model.TipoServicioReserva
import com.example.balneapp.ui.viewmodel.DisponibilidadViewModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.util.Locale

private val fondoDisponibilidad = Color(0xFFFCF8F2)
private val turquesaDisponibilidad = Color(0xFF079EAD)
private val formatoPrecio = NumberFormat.getIntegerInstance(Locale("es", "AR"))
private val formatoFecha = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "AR"))

@Composable
fun PantallaDisponibilidad(
    navController: NavController,
    balnearioId: String,
    viewModel: DisponibilidadViewModel = viewModel(key = "disponibilidad-$balnearioId")
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let { mensaje ->
            snackbarHostState.showSnackbar(mensaje)
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        containerColor = fondoDisponibilidad,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(fondoDisponibilidad)
        ) {
            EncabezadoFlujoReserva(
                titulo = "Disponibilidad",
                onVolver = { navController.popBackStack() }
            )

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 18.dp)
                        .padding(bottom = 92.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    SelectorTipoServicio(
                        seleccionado = uiState.tipoServicio,
                        onSeleccionar = viewModel::seleccionarServicio
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    CalendarioDisponibilidad(
                        mes = uiState.mesVisible,
                        fechaSeleccionada = uiState.fechaSeleccionada,
                        fechaMinima = LocalDate.now().plusDays(1),
                        onMesAnterior = { viewModel.cambiarMes(-1) },
                        onMesSiguiente = { viewModel.cambiarMes(1) },
                        onSeleccionarDia = viewModel::seleccionarDia,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formatoFecha.format(uiState.fechaSeleccionada)
                                .replaceFirstChar { it.titlecase(Locale("es", "AR")) },
                            color = Color(0xFF202020),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Precios por día",
                            color = Color(0xFF6B6B6B),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    uiState.opcionesDisponibles.forEach { opcion ->
                        OpcionEquipamiento(
                            opcion = opcion,
                            cantidad = uiState.cantidades[opcion.id] ?: 0,
                            tipoServicio = uiState.tipoServicio,
                            onCambiarCantidad = { delta -> viewModel.cambiarCantidad(opcion.id, delta) }
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                    }
                }

                Button(
                    onClick = {
                        if (viewModel.continuar(balnearioId)) {
                            navController.navigate("resumenReserva")
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 14.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = turquesaDisponibilidad),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Continuar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun EncabezadoFlujoReserva(titulo: String, onVolver: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(Color.White)
    ) {
        IconButton(
            onClick = onVolver,
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 8.dp).size(42.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_balneapp_back),
                contentDescription = "Volver",
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = titulo,
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xFF171717),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SelectorTipoServicio(
    seleccionado: TipoServicioReserva,
    onSeleccionar: (TipoServicioReserva) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFEDEAE6),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            TipoServicioReserva.entries.forEach { tipo ->
                val activo = tipo == seleccionado
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (activo) turquesaDisponibilidad else Color.Transparent)
                        .clickable { onSeleccionar(tipo) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tipo.etiqueta,
                        color = if (activo) Color.White else Color(0xFF444444),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun OpcionEquipamiento(
    opcion: OpcionEquipamientoReserva,
    cantidad: Int,
    tipoServicio: TipoServicioReserva,
    onCambiarCantidad: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    if (tipoServicio == TipoServicioReserva.CARPA) R.drawable.ic_balneapp_tent
                    else R.drawable.ic_sombrilla
                ),
                contentDescription = null,
                modifier = Modifier.size(42.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(9.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(opcion.nombre, color = Color(0xFF252525), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Text(opcion.capacidad, color = Color(0xFF747474), fontSize = 10.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$" + formatoPrecio.format(opcion.precioPorDia),
                    color = Color(0xFF242424),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ControlCantidad(signo = "−", descripcion = "Quitar unidad", onClick = { onCambiarCantidad(-1) })
                    Text(
                        text = cantidad.toString(),
                        modifier = Modifier.width(22.dp),
                        color = Color(0xFF242424),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    ControlCantidad(signo = "+", descripcion = "Agregar unidad", onClick = { onCambiarCantidad(1) })
                }
            }
        }
    }
}

@Composable
private fun ControlCantidad(
    signo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(28.dp)
    ) {
        Text(signo, color = Color(0xFF555555), fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}
