package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.balneapp.data.repository.ReservasRepository
import com.example.balneapp.ui.model.CatalogoBalneariosReserva
import com.example.balneapp.ui.model.CatalogoEquipamientoReserva
import com.example.balneapp.ui.model.DisponibilidadUiState
import com.example.balneapp.ui.model.EquipoReserva
import com.example.balneapp.ui.model.ReservaBorrador
import com.example.balneapp.ui.model.TipoServicioReserva
import java.time.LocalDate
import java.time.YearMonth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DisponibilidadViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DisponibilidadUiState())
    val uiState: StateFlow<DisponibilidadUiState> = _uiState.asStateFlow()

    fun cambiarMes(delta: Long) {
        val estado = _uiState.value
        val nuevoMes = estado.mesVisible.plusMonths(delta)
        if (nuevoMes.isBefore(YearMonth.now())) return
        val nuevoDia = estado.fechaSeleccionada.dayOfMonth.coerceAtMost(nuevoMes.lengthOfMonth())
        _uiState.value = estado.copy(
            mesVisible = nuevoMes,
            fechaSeleccionada = nuevoMes.atDay(nuevoDia)
        )
    }

    fun seleccionarDia(dia: Int) {
        val estado = _uiState.value
        if (dia in 1..estado.mesVisible.lengthOfMonth()) {
            val fecha = estado.mesVisible.atDay(dia)
            if (!fecha.isBefore(LocalDate.now().plusDays(1))) {
                _uiState.value = estado.copy(fechaSeleccionada = fecha)
            }
        }
    }

    fun seleccionarServicio(tipo: TipoServicioReserva) {
        _uiState.value = _uiState.value.copy(tipoServicio = tipo, mensaje = null)
    }

    fun cambiarCantidad(opcionId: String, delta: Int) {
        val estado = _uiState.value
        val esOpcionVisible = estado.opcionesDisponibles.any { it.id == opcionId }
        if (!esOpcionVisible) return

        val cantidadActual = estado.cantidades[opcionId] ?: 0
        _uiState.value = estado.copy(
            cantidades = estado.cantidades + (opcionId to (cantidadActual + delta).coerceIn(0, 9)),
            mensaje = null
        )
    }

    fun continuar(balnearioId: String): Boolean {
        val estado = _uiState.value
        val balneario = CatalogoBalneariosReserva.buscar(balnearioId)
        if (balneario == null) {
            _uiState.value = estado.copy(mensaje = "No encontramos este balneario.")
            return false
        }

        val equiposSeleccionados = estado.opcionesDisponibles.mapNotNull { opcion ->
            val cantidad = estado.cantidades[opcion.id] ?: 0
            if (cantidad == 0) null else EquipoReserva(
                nombre = opcion.nombre,
                cantidad = cantidad,
                precioPorUnidad = opcion.precioPorDia
            )
        }
        if (equiposSeleccionados.isEmpty()) {
            _uiState.value = estado.copy(mensaje = "Elegí al menos un servicio para continuar.")
            return false
        }

        ReservasRepository.guardarBorrador(
            ReservaBorrador(
                balneario = balneario,
                fecha = estado.fechaSeleccionada,
                tipoServicio = estado.tipoServicio,
                equipamiento = equiposSeleccionados,
                personas = 2
            )
        )
        _uiState.value = estado.copy(mensaje = null)
        return true
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }
}
