package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.balneapp.navigation.navigateToBalneAppTab
import com.example.balneapp.ui.viewmodel.ResumenReservaViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

private val fondoResumen = Color(0xFFFCF8F2)
private val turquesaResumen = Color(0xFF079EAD)
private val formatoMoneda = NumberFormat.getIntegerInstance(Locale("es", "AR"))
private val formatoFechaResumen = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "AR"))

@Composable
fun PantallaResumenReserva(
    navController: NavController,
    viewModel: ResumenReservaViewModel = balneAppViewModel()
) {
    val borrador by viewModel.borrador.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val reservaBorrador = borrador

    LaunchedEffect(uiState.confirmada) {
        if (uiState.confirmada) {
            navController.navigateToBalneAppTab("reservas")
            viewModel.limpiarConfirmacion()
        }
    }

    Scaffold(containerColor = fondoResumen) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(fondoResumen)
        ) {
            EncabezadoFlujoReserva(
                titulo = "Resumen de reserva",
                onVolver = { navController.popBackStack() }
            )

            if (reservaBorrador == null) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No hay una reserva para resumir.", color = Color(0xFF555555), fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigateToBalneAppTab("principal") }) {
                        Text("Volver al inicio")
                    }
                }
            } else {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 18.dp)
                            .padding(bottom = 96.dp)
                    ) {
                        Spacer(modifier = Modifier.height(18.dp))
                        ResumenBalneario(
                            nombre = reservaBorrador.balneario.nombre,
                            direccion = reservaBorrador.balneario.direccion,
                            imagenResId = reservaBorrador.balneario.imagenResId
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White)
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            FilaResumen(
                                etiqueta = "Fecha",
                                valor = formatoFechaResumen.format(reservaBorrador.fecha)
                                    .replaceFirstChar { it.titlecase(Locale("es", "AR")) }
                            )
                            FilaResumen(etiqueta = "Servicio", valor = reservaBorrador.servicioResumen)
                            FilaResumen(etiqueta = "Personas", valor = "${reservaBorrador.personas} personas")
                            FilaResumen(
                                etiqueta = "Precio por día",
                                valor = "$" + formatoMoneda.format(reservaBorrador.precioPorDia)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 13.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total", color = Color(0xFF242424), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text(
                                    text = "$" + formatoMoneda.format(reservaBorrador.total),
                                    color = Color(0xFF171717),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF3C9F71),
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(
                                text = "Cancelación gratuita hasta 24 hs antes",
                                color = Color(0xFF4E795D),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Button(
                        onClick = viewModel::confirmarBorrador,
                        enabled = !uiState.estaConfirmando,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(0.9f)
                            .padding(bottom = 16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = turquesaResumen),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = if (uiState.estaConfirmando) "Guardando reserva…" else "Confirmar reserva",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    uiState.mensajeError?.let { mensaje ->
                        Text(
                            text = mensaje,
                            color = Color(0xFFB3261E),
                            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 70.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ResumenBalneario(nombre: String, direccion: String, imagenResId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(imagenResId),
            contentDescription = nombre,
            modifier = Modifier.size(width = 92.dp, height = 78.dp).clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(nombre, color = Color(0xFF242424), fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(direccion, color = Color(0xFF777777), fontSize = 10.sp)
        }
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(etiqueta, color = Color(0xFF555555), fontSize = 13.sp)
        Text(valor, color = Color(0xFF2B2B2B), fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
