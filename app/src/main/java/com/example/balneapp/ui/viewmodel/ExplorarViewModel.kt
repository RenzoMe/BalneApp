package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balneapp.data.repository.UbicacionRepository
import com.example.balneapp.ui.model.UbicacionUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExplorarViewModel(
    private val ubicacionRepository: UbicacionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UbicacionUiState())
    val uiState: StateFlow<UbicacionUiState> = _uiState.asStateFlow()

    fun cargarUbicacion() {
        if (_uiState.value.estaCargando) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(estaCargando = true, mensaje = null)
            val ubicacion = try {
                ubicacionRepository.obtenerUbicacionAproximada()
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                null
            }
            _uiState.value = if (ubicacion == null) {
                UbicacionUiState(mensaje = "No encontramos tu ubicación. Verificá que esté activada y probá otra vez.")
            } else {
                UbicacionUiState(ubicacion = ubicacion)
            }
        }
    }

    fun mostrarPermisoDenegado() {
        _uiState.value = UbicacionUiState(
            mensaje = "Sin permiso de ubicación, no podemos mostrar tu posición aproximada."
        )
    }
}
