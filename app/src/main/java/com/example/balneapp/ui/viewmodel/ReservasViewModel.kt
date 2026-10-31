package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.balneapp.data.repository.ReservasRepository

class ReservasViewModel : ViewModel() {
    val reservas = ReservasRepository.reservas
}
