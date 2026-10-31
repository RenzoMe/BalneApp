package com.example.balneapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.balneapp.data.repository.FavoritosRepository

@Composable
fun BotonFavoritoBalneario(
    balnearioId: String,
    modifier: Modifier = Modifier,
    tamanoBoton: Dp = 42.dp,
    tamanoIcono: Dp = 22.dp,
    colorActivo: Color = Color(0xFF00AFC2),
    colorInactivo: Color = Color(0xFF777777),
    colorFondo: Color = Color.White.copy(alpha = 0.92f)
) {
    val idsFavoritos by FavoritosRepository.idsFavoritos.collectAsState()
    val esFavorito = balnearioId in idsFavoritos

    Box(
        modifier = modifier
            .size(tamanoBoton)
            .background(colorFondo, CircleShape)
            .clickable(role = Role.Button) {
                FavoritosRepository.alternar(balnearioId)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
            tint = if (esFavorito) colorActivo else colorInactivo,
            modifier = Modifier.size(tamanoIcono)
        )
    }
}
