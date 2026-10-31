package com.example.balneapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.balneapp.R
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private val diasDeLaSemana = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sá", "Do")
private val mesDeDiseno = YearMonth.of(2026, 5)

@Composable
fun CalendarioReserva(
    mes: YearMonth,
    diasSeleccionados: Set<Int>,
    mostrarCalendarioInteractivo: Boolean,
    onMesAnterior: () -> Unit,
    onMesSiguiente: () -> Unit,
    onSeleccionarDia: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (mes == mesDeDiseno && !mostrarCalendarioInteractivo) {
        CalendarioDeDiseno(
            onMesAnterior = onMesAnterior,
            onMesSiguiente = onMesSiguiente,
            onSeleccionarDia = onSeleccionarDia,
            modifier = modifier
        )
    } else {
        CalendarioInteractivo(
            mes = mes,
            diasSeleccionados = diasSeleccionados,
            onMesAnterior = onMesAnterior,
            onMesSiguiente = onMesSiguiente,
            onSeleccionarDia = onSeleccionarDia,
            modifier = modifier
        )
    }
}

@Composable
private fun CalendarioDeDiseno(
    onMesAnterior: () -> Unit,
    onMesSiguiente: () -> Unit,
    onSeleccionarDia: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(278.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.reserva_calendario_mayo_2026),
            contentDescription = "Calendario de mayo de 2026",
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 4.dp, top = 3.dp)
                .size(42.dp)
                .clickable(onClick = onMesAnterior)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 4.dp, top = 3.dp)
                .size(42.dp)
                .clickable(onClick = onMesSiguiente)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = maxHeight * 0.33f)
                .height(maxHeight * 0.64f)
                .padding(horizontal = 3.dp)
        ) {
            repeat(5) { row ->
                Row(modifier = Modifier.weight(1f)) {
                    repeat(7) { column ->
                        val day = row * 7 + column
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(enabled = day in 1..31 && day != 22) {
                                    onSeleccionarDia(day)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarioInteractivo(
    mes: YearMonth,
    diasSeleccionados: Set<Int>,
    onMesAnterior: () -> Unit,
    onMesSiguiente: () -> Unit,
    onSeleccionarDia: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val monthName = mes.month.getDisplayName(TextStyle.FULL, Locale("es", "AR"))
        .replaceFirstChar { it.titlecase(Locale("es", "AR")) }
    val firstOffset = if (mes == mesDeDiseno) 1 else mes.atDay(1).dayOfWeek.value - 1
    val rowCount = (firstOffset + mes.lengthOfMonth() + 6) / 7

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(278.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(40.dp), contentAlignment = Alignment.Center) {
            IconButton(
                onClick = onMesAnterior,
                modifier = Modifier.align(Alignment.CenterStart).size(38.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Mes anterior", tint = Color(0xFF222222))
            }
            Text(text = "$monthName ${mes.year}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            IconButton(
                onClick = onMesSiguiente,
                modifier = Modifier.align(Alignment.CenterEnd).size(38.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Mes siguiente", tint = Color(0xFF222222))
            }
        }

        Row(modifier = Modifier.fillMaxWidth().height(27.dp), verticalAlignment = Alignment.CenterVertically) {
            diasDeLaSemana.forEach { dayLabel ->
                Text(
                    text = dayLabel,
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            repeat(rowCount) { row ->
                Row(modifier = Modifier.weight(1f)) {
                    repeat(7) { column ->
                        val day = row * 7 + column - firstOffset + 1
                        val validDay = day in 1..mes.lengthOfMonth()
                        val unavailable = mes == mesDeDiseno && day == 22
                        val selected = day in diasSeleccionados

                        Box(
                            modifier = Modifier.weight(1f).fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (validDay) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                selected -> Color(0xFF8CD8E3)
                                                unavailable -> Color(0xFFD2D2D2)
                                                else -> Color.Transparent
                                            }
                                        )
                                        .clickable(enabled = !unavailable) { onSeleccionarDia(day) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        color = if (unavailable) Color(0xFF8B8B8B) else Color(0xFF171717),
                                        fontSize = 18.sp,
                                        textDecoration = if (unavailable) TextDecoration.LineThrough else TextDecoration.None
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
