package com.example.balneapp.ui.model

import com.example.balneapp.data.repository.UbicacionActual

data class UbicacionUiState(
    val estaCargando: Boolean = false,
    val ubicacion: UbicacionActual? = null,
    val mensaje: String? = null
)
