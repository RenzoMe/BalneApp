package com.example.balneapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.balneapp.data.repository.FavoritosRepository
import com.example.balneapp.R

@Composable
fun BotonFavoritoBalneario(
    balnearioId: String,
    repository: FavoritosRepository,
    modifier: Modifier = Modifier,
    tamanoBoton: Dp = 42.dp,
    tamanoIcono: Dp = 22.dp,
    colorActivo: Color = Color(0xFF00AFC2),
    colorInactivo: Color = Color(0xFF777777),
    colorFondo: Color = Color.White.copy(alpha = 0.92f)
) {
    val idsFavoritos by repository.idsFavoritos.collectAsState(initial = emptySet())
    val esFavorito = balnearioId in idsFavoritos

    Box(
        modifier = modifier
            .size(tamanoBoton)
            .background(colorFondo, CircleShape)
            .clickable(role = Role.Button) {
                androidx.compose.runtime.LaunchedEffect(Unit) { }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                if (esFavorito) R.drawable.ic_balneapp_favorite_filled else R.drawable.ic_balneapp_favorite
            ),
            contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
            modifier = Modifier.size(tamanoIcono),
            colorFilter = ColorFilter.tint(if (esFavorito) colorActivo else colorInactivo)
        )
    }
}
