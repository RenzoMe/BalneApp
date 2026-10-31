package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balneapp.R
import com.example.balneapp.data.repository.FavoritosRepository
import com.example.balneapp.ui.model.BalnearioFavoritoUi
import com.example.balneapp.ui.model.BalnearioIds
import com.example.balneapp.ui.model.FavoritosUiState
import com.example.balneapp.ui.model.OrdenFavoritos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class FavoritosViewModel : ViewModel() {
    private val balnearios = listOf(
        BalnearioFavoritoUi(
            id = BalnearioIds.GOLONDRINAS,
            nombre = "Balneario Golondrinas",
            direccion = "Av. costanera y 27",
            puntuacion = 4.4,
            cantidadResenas = 134,
            precioDesde = 30_000,
            imagenResId = R.drawable.imagengolondrina,
            rutaDetalle = "golondrinas"
        ),
        BalnearioFavoritoUi(
            id = BalnearioIds.OASIS,
            nombre = "Balneario OASIS",
            direccion = "Av. costanera y 29",
            puntuacion = 3.8,
            cantidadResenas = 103,
            precioDesde = 30_000,
            imagenResId = R.drawable.imagenpaco,
            rutaDetalle = "oasis"
        ),
        BalnearioFavoritoUi(
            id = BalnearioIds.HORIZONTE,
            nombre = "Balneario Horizonte",
            direccion = "A 1,2 km de ti",
            puntuacion = 4.8,
            cantidadResenas = 320,
            precioDesde = 25_000,
            imagenResId = R.drawable.imagengolondrina
        )
    )

    private val _orden = MutableStateFlow(OrdenFavoritos.RECOMENDADOS)
    val uiState: StateFlow<FavoritosUiState> = combine(
        FavoritosRepository.idsFavoritos,
        _orden
    ) { idsFavoritos, orden ->
        val favoritos = balnearios.filter { it.id in idsFavoritos }
        val ordenados = when (orden) {
            OrdenFavoritos.RECOMENDADOS -> favoritos
            OrdenFavoritos.MEJOR_PUNTUACION -> favoritos.sortedByDescending { it.puntuacion }
            OrdenFavoritos.MENOR_PRECIO -> favoritos.sortedBy { it.precioDesde }
        }
        FavoritosUiState(favoritos = ordenados, orden = orden)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FavoritosUiState(
            favoritos = balnearios.filter { it.id in FavoritosRepository.idsFavoritos.value }
        )
    )

    fun quitarFavorito(id: String) {
        FavoritosRepository.quitar(id)
    }

    fun ordenarPor(orden: OrdenFavoritos) {
        _orden.value = orden
    }
}
