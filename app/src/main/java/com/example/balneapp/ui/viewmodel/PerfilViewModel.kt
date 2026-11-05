package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balneapp.data.repository.PreferenciasUsuario
import com.example.balneapp.data.repository.PreferenciasUsuarioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repository: PreferenciasUsuarioRepository
) : ViewModel() {
    val preferencias: StateFlow<PreferenciasUsuario> = repository.preferencias.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PreferenciasUsuario()
    )

    val sesionIniciada: StateFlow<Boolean?> = repository.sesionIniciada
        .map<Boolean, Boolean?> { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun iniciarSesion(email: String, alCompletar: () -> Unit) {
        viewModelScope.launch {
            repository.iniciarSesion(email)
            alCompletar()
        }
    }

    fun registrarCuenta(nombre: String, email: String, alCompletar: () -> Unit) {
        viewModelScope.launch {
            repository.registrarCuenta(nombre, email)
            alCompletar()
        }
    }

    fun cerrarSesion(alCompletar: () -> Unit) {
        viewModelScope.launch {
            repository.cerrarSesion()
            alCompletar()
        }
    }

    fun guardarPerfil(nombre: String, email: String, alCompletar: () -> Unit = {}) {
        val nombreLimpio = nombre.trim()
        val emailLimpio = email.trim()
        if (nombreLimpio.isEmpty() || emailLimpio.isEmpty()) return

        viewModelScope.launch {
            repository.guardarPerfil(nombreLimpio, emailLimpio)
            alCompletar()
        }
    }

    fun establecerNotificaciones(habilitadas: Boolean) {
        viewModelScope.launch {
            repository.establecerNotificaciones(habilitadas)
        }
    }

    fun guardarTarjeta(marca: String, ultimosDigitos: String) {
        viewModelScope.launch { repository.guardarTarjeta(marca, ultimosDigitos) }
    }

    fun quitarTarjeta(tarjeta: String) {
        viewModelScope.launch { repository.quitarTarjeta(tarjeta) }
    }
}
