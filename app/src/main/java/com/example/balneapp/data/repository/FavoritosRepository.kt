package com.example.balneapp.data.repository

import com.example.balneapp.ui.model.BalnearioIds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Estado en memoria compartido por las pantallas que muestran balnearios. */
object FavoritosRepository {
    private val _idsFavoritos = MutableStateFlow(
        setOf(BalnearioIds.GOLONDRINAS, BalnearioIds.OASIS)
    )
    val idsFavoritos: StateFlow<Set<String>> = _idsFavoritos.asStateFlow()

    fun alternar(id: String) {
        _idsFavoritos.update { actuales ->
            if (id in actuales) actuales - id else actuales + id
        }
    }

    fun quitar(id: String) {
        _idsFavoritos.update { actuales -> actuales - id }
    }
}
