package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.balneapp.ui.model.EquipamientoReserva
import com.example.balneapp.ui.model.ReservasUiState
import java.time.YearMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReservasViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReservasUiState())
    val uiState: StateFlow<ReservasUiState> = _uiState.asStateFlow()

    fun mesAnterior() = cambiarMes(-1)

    fun mesSiguiente() = cambiarMes(1)

    private fun cambiarMes(cantidad: Long) {
        _uiState.value = _uiState.value.copy(
            mes = _uiState.value.mes.plusMonths(cantidad),
            diasSeleccionados = emptySet(),
            primerDiaSeleccionado = null,
            mostrarCalendarioInteractivo = true
        )
    }

    fun seleccionarDia(dia: Int) {
        val estado = _uiState.value
        if (dia !in 1..estado.mes.lengthOfMonth()) return
        if (estado.mes == YearMonth.of(2026, 5) && dia == 22) return

        val inicio = estado.primerDiaSeleccionado
        if (inicio == null) {
            _uiState.value = estado.copy(
                diasSeleccionados = setOf(dia),
                primerDiaSeleccionado = dia,
                mostrarCalendarioInteractivo = true
            )
        } else {
            val rango = (minOf(inicio, dia)..maxOf(inicio, dia)).filterNot {
                estado.mes == YearMonth.of(2026, 5) && it == 22
            }.toSet()
            _uiState.value = estado.copy(
                diasSeleccionados = rango,
                primerDiaSeleccionado = null,
                mostrarCalendarioInteractivo = true
            )
        }
    }

    fun cambiarPersonas(delta: Int) {
        val nuevaCantidad = (_uiState.value.personas + delta).coerceIn(1, 12)
        _uiState.value = _uiState.value.copy(personas = nuevaCantidad)
    }

    fun alternarEquipamiento(equipamiento: EquipamientoReserva) {
        val actual = _uiState.value.equipamiento
        val actualizado = if (equipamiento in actual) actual - equipamiento else actual + equipamiento
        _uiState.value = _uiState.value.copy(equipamiento = actualizado)
    }

    fun confirmarReserva() {
        _uiState.value = _uiState.value.copy(
            mensaje = "La reserva está lista. El pago todavía no está integrado."
        )
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }
}
