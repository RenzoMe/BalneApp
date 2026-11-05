package com.example.balneapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.balneapp.R

private data class ServicioBalnearioIcono(
    val nombre: String,
    val recursoIcono: Int
)

private val serviciosBalneario = listOf(
    ServicioBalnearioIcono("Carpas", R.drawable.ic_balneapp_tent),
    ServicioBalnearioIcono("Estacionamiento", R.drawable.ic_balneapp_parking),
    ServicioBalnearioIcono("Duchas", R.drawable.ic_balneapp_shower),
    ServicioBalnearioIcono("Restaurante", R.drawable.ic_balneapp_restaurant),
    ServicioBalnearioIcono("WiFi", R.drawable.ic_balneapp_wifi),
    ServicioBalnearioIcono("Baños", R.drawable.ic_balneapp_bathroom),
    ServicioBalnearioIcono("Accesibilidad", R.drawable.ic_balneapp_accessibility),
    ServicioBalnearioIcono("Pet friendly", R.drawable.ic_balneapp_pet_friendly),
    ServicioBalnearioIcono("Quincho", R.drawable.ic_balneapp_quincho),
    ServicioBalnearioIcono("Kiosco", R.drawable.ic_balneapp_kiosk),
    ServicioBalnearioIcono("Pileta", R.drawable.ic_balneapp_pool),
    ServicioBalnearioIcono("Juegos", R.drawable.ic_balneapp_playground),
    ServicioBalnearioIcono("Canchas", R.drawable.ic_balneapp_sports_court),
    ServicioBalnearioIcono("Sombrillas", R.drawable.ic_sombrilla),
    ServicioBalnearioIcono("Acceso al mar", R.drawable.ic_balneapp_waves)
)

@Composable
fun FilaServiciosBalneario() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(serviciosBalneario, key = { it.nombre }) { servicio ->
            Column(
                modifier = Modifier.width(56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(servicio.recursoIcono),
                    contentDescription = servicio.nombre,
                    modifier = Modifier.size(34.dp)
                )
                Text(
                    text = servicio.nombre,
                    textAlign = TextAlign.Center,
                    fontSize = 9.sp,
                    lineHeight = 11.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
