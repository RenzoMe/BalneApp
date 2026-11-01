package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balneapp.data.repository.PreferenciasUsuario
import com.example.balneapp.data.repository.PreferenciasUsuarioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    fun guardarPerfil(nombre: String, email: String) {
        val nombreLimpio = nombre.trim()
        val emailLimpio = email.trim()
        if (nombreLimpio.isEmpty() || emailLimpio.isEmpty()) return

        viewModelScope.launch {
            repository.guardarPerfil(nombreLimpio, emailLimpio)
        }
    }

    fun establecerNotificaciones(habilitadas: Boolean) {
        viewModelScope.launch {
            repository.establecerNotificaciones(habilitadas)
        }
    }
}
