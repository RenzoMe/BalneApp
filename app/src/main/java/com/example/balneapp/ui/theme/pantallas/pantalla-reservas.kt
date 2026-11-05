package com.example.balneapp.ui.theme.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.balneapp.R
import com.example.balneapp.navigation.AppRoute
import com.example.balneapp.navigation.navigateToTopLevel
import com.example.balneapp.ui.model.ReservaConfirmada
import com.example.balneapp.ui.viewmodel.ReservasViewModel
import com.example.balneapp.ui.viewmodel.balneAppViewModel
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

private val fondoListaReservas = Color(0xFFFDF8F4)
private val formatoPrecioReserva = NumberFormat.getIntegerInstance(Locale("es", "AR"))
private val formatoFechaReserva = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", Locale("es", "AR"))

@Composable
fun PantallaReservas(
    navController: NavController,
    viewModel: ReservasViewModel = balneAppViewModel()
) {
    val reservas by viewModel.reservas.collectAsState()

    Scaffold(containerColor = fondoListaReservas) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(fondoListaReservas)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            Image(
                painter = painterResource(R.drawable.balneapp),
                contentDescription = "BalneApp",
                modifier = Modifier.width(160.dp).height(34.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Tus reservas",
                color = Color(0xFF171717),
                fontSize = 26.sp,
                lineHeight = 31.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (reservas.isEmpty()) "Consultá acá los balnearios que reservaste."
                else "Tenés ${reservas.size} reserva${if (reservas.size == 1) "" else "s"} confirmada${if (reservas.size == 1) "" else "s"}.",
                color = Color(0xFF737373),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (reservas.isEmpty()) {
                EstadoReservasVacio(
                    onExplorar = { navController.navigateToTopLevel(AppRoute.Inicio) }
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    reservas.chunked(2).forEach { fila ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            fila.forEach { reserva ->
                                TarjetaReserva(
                                    reserva = reserva,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (fila.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun EstadoReservasVacio(onExplorar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 68.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.misreservas),
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Todavía no tenés reservas",
            color = Color(0xFF292929),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Cuando confirmes una, vas a verla acá.",
            color = Color(0xFF777777),
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = onExplorar) {
            Text("Explorar balnearios")
        }
    }
}

@Composable
private fun TarjetaReserva(reserva: ReservaConfirmada, modifier: Modifier = Modifier) {
    val detalle = reserva.detalle

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFCFF7FC)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(detalle.balneario.imagenResId),
                    contentDescription = detalle.balneario.nombre,
                    modifier = Modifier.fillMaxWidth().height(106.dp),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.94f))
                        .padding(horizontal = 7.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF32986B),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("Confirmada", color = Color(0xFF3B7659), fontSize = 8.sp, fontWeight = FontWeight.Medium)
                }
            }

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp)) {
                Text(
                    text = detalle.balneario.nombre,
                    color = Color(0xFF1E1E1E),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatoFechaReserva.format(detalle.fecha)
                        .replaceFirstChar { it.titlecase(Locale("es", "AR")) },
                    color = Color(0xFF555555),
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_balneapp_profile),
                        contentDescription = "Personas",
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${detalle.personas} personas · ${detalle.servicioResumen}",
                        color = Color(0xFF666666),
                        fontSize = 9.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "$" + formatoPrecioReserva.format(detalle.total),
                    color = Color(0xFF111111),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
