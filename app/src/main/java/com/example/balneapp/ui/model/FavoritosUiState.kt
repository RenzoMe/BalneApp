package com.example.balneapp.ui.model

data class BalnearioFavoritoUi(
    val id: String,
    val nombre: String,
    val direccion: String,
    val puntuacion: Double,
    val cantidadResenas: Int,
    val precioDesde: Int,
    val imagenResId: Int,
)

enum class OrdenFavoritos {
    RECOMENDADOS,
    MEJOR_PUNTUACION,
    MENOR_PRECIO
}

data class FavoritosUiState(
    val favoritos: List<BalnearioFavoritoUi> = emptyList(),
    val orden: OrdenFavoritos = OrdenFavoritos.RECOMENDADOS
)
