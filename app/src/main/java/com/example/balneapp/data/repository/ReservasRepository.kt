package com.example.balneapp.data.repository

import com.example.balneapp.ui.model.ReservaBorrador
import com.example.balneapp.ui.model.ReservaConfirmada
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Fuente compartida del borrador y de las reservas confirmadas durante la sesión. */
object ReservasRepository {
    private val _borrador = MutableStateFlow<ReservaBorrador?>(null)
    val borrador: StateFlow<ReservaBorrador?> = _borrador.asStateFlow()

    private val _reservas = MutableStateFlow<List<ReservaConfirmada>>(emptyList())
    val reservas: StateFlow<List<ReservaConfirmada>> = _reservas.asStateFlow()

    fun guardarBorrador(reserva: ReservaBorrador) {
        _borrador.value = reserva
    }

    fun confirmarBorrador(): ReservaConfirmada? {
        val borradorActual = _borrador.value ?: return null
        val reserva = ReservaConfirmada(id = UUID.randomUUID().toString(), detalle = borradorActual)
        _reservas.update { actuales -> listOf(reserva) + actuales }
        _borrador.value = null
        return reserva
    }
}
