package com.example.balneapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private val diasSemana = listOf("LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB", "DOM")
private val idioma = Locale("es", "AR")

@Composable
fun CalendarioDisponibilidad(
    mes: YearMonth,
    fechaSeleccionada: LocalDate,
    fechaMinima: LocalDate,
    onMesAnterior: () -> Unit,
    onMesSiguiente: () -> Unit,
    onSeleccionarDia: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val nombreMes = mes.month.getDisplayName(TextStyle.FULL, idioma)
        .replaceFirstChar { it.titlecase(idioma) }
    val desplazamientoInicial = mes.atDay(1).dayOfWeek.value - 1
    val cantidadFilas = (desplazamientoInicial + mes.lengthOfMonth() + 6) / 7

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(42.dp), contentAlignment = Alignment.Center) {
            IconButton(
                onClick = onMesAnterior,
                enabled = mes > YearMonth.from(fechaMinima),
                modifier = Modifier.align(Alignment.CenterStart).size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Mes anterior",
                    tint = if (mes > YearMonth.from(fechaMinima)) Color(0xFF656565) else Color(0xFFCCCCCC)
                )
            }
            Text(
                text = "$nombreMes ${mes.year}",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF202020)
            )
            IconButton(
                onClick = onMesSiguiente,
                modifier = Modifier.align(Alignment.CenterEnd).size(38.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Mes siguiente",
                    tint = Color(0xFF656565)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            diasSemana.forEach { dia ->
                Text(
                    text = dia,
                    modifier = Modifier.weight(1f).padding(vertical = 6.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8C8C8C),
                    textAlign = TextAlign.Center
                )
            }
        }

        repeat(cantidadFilas) { fila ->
            Row(
                modifier = Modifier.fillMaxWidth().height(40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(7) { columna ->
                    val dia = fila * 7 + columna - desplazamientoInicial + 1
                    val valido = dia in 1..mes.lengthOfMonth()
                    if (valido) {
                        val seleccionado = mes.atDay(dia) == fechaSeleccionada
                        val habilitado = !mes.atDay(dia).isBefore(fechaMinima)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(if (seleccionado) Color(0xFF079EAD) else Color.Transparent)
                                .clickable(enabled = habilitado) { onSeleccionarDia(dia) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dia.toString(),
                                fontSize = 15.sp,
                                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                                color = when {
                                    seleccionado -> Color.White
                                    habilitado -> Color(0xFF292929)
                                    else -> Color(0xFFBBBBBB)
                                }
                            )
                        }
                    } else {
                        Box(modifier = Modifier.weight(1f).size(34.dp))
                    }
                }
            }
        }
    }
}
