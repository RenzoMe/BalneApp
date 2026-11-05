package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balneapp.data.repository.ReservasRepository
import com.example.balneapp.ui.model.ReservaBorrador
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ResumenReservaUiState(
    val estaConfirmando: Boolean = false,
    val confirmada: Boolean = false,
    val mensajeError: String? = null
)

class ResumenReservaViewModel(
    private val repository: ReservasRepository
) : ViewModel() {
    val borrador: StateFlow<ReservaBorrador?> = repository.borrador

    private val _uiState = MutableStateFlow(ResumenReservaUiState())
    val uiState: StateFlow<ResumenReservaUiState> = _uiState.asStateFlow()

    fun confirmarBorrador() {
        if (_uiState.value.estaConfirmando) return

        viewModelScope.launch {
            _uiState.value = ResumenReservaUiState(estaConfirmando = true)
            try {
                val reserva = repository.confirmarBorrador()
                _uiState.value = if (reserva == null) {
                    ResumenReservaUiState(mensajeError = "No hay una reserva para confirmar.")
                } else {
                    ResumenReservaUiState(confirmada = true)
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _uiState.value = ResumenReservaUiState(
                    mensajeError = "No pudimos guardar la reserva. Intentá nuevamente."
                )
            }
        }
    }

    fun limpiarConfirmacion() {
        _uiState.value = ResumenReservaUiState()
    }
}
