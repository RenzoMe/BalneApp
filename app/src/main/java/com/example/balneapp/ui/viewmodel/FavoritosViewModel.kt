package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.balneapp.R
import com.example.balneapp.ui.model.BalnearioFavoritoUi
import com.example.balneapp.ui.model.FavoritosUiState
import com.example.balneapp.ui.model.OrdenFavoritos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritosViewModel : ViewModel() {
    private val favoritosIniciales = listOf(
        BalnearioFavoritoUi(
            id = "golondrinas",
            nombre = "Balneario Golondrinas",
            direccion = "Av. costanera y 27",
            puntuacion = 4.4,
            cantidadResenas = 134,
            precioDesde = 30_000,
            imagenResId = R.drawable.imagengolondrina,
            rutaDetalle = "golondrinas"
        ),
        BalnearioFavoritoUi(
            id = "paco",
            nombre = "Balneario PACO",
            direccion = "Av. costanera y 27",
            puntuacion = 3.8,
            cantidadResenas = 103,
            precioDesde = 30_000,
            imagenResId = R.drawable.imagenpaco
        )
    )

    private val _uiState = MutableStateFlow(
        FavoritosUiState(favoritos = favoritosIniciales)
    )
    val uiState: StateFlow<FavoritosUiState> = _uiState.asStateFlow()

    fun quitarFavorito(id: String) {
        _uiState.value = _uiState.value.copy(
            favoritos = _uiState.value.favoritos.filterNot { it.id == id }
        )
    }

    fun ordenarPor(orden: OrdenFavoritos) {
        val actuales = _uiState.value.favoritos
        val ordenados = when (orden) {
            OrdenFavoritos.RECOMENDADOS -> favoritosIniciales.filter { original ->
                actuales.any { it.id == original.id }
            }
            OrdenFavoritos.MEJOR_PUNTUACION -> actuales.sortedByDescending { it.puntuacion }
            OrdenFavoritos.MENOR_PRECIO -> actuales.sortedBy { it.precioDesde }
        }
        _uiState.value = _uiState.value.copy(favoritos = ordenados, orden = orden)
    }
}
