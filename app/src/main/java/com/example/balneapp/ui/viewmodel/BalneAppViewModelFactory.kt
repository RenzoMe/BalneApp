package com.example.balneapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.balneapp.AppContainer

class BalneAppViewModelFactory(
    private val container: AppContainer
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when {
            modelClass.isAssignableFrom(FavoritosViewModel::class.java) ->
                FavoritosViewModel(container.favoritosRepository)
            modelClass.isAssignableFrom(ReservasViewModel::class.java) ->
                ReservasViewModel(container.reservasRepository)
            modelClass.isAssignableFrom(DisponibilidadViewModel::class.java) ->
                DisponibilidadViewModel(container.reservasRepository)
            modelClass.isAssignableFrom(ResumenReservaViewModel::class.java) ->
                ResumenReservaViewModel(container.reservasRepository)
            modelClass.isAssignableFrom(PerfilViewModel::class.java) ->
                PerfilViewModel(container.preferenciasRepository)
            modelClass.isAssignableFrom(ExplorarViewModel::class.java) ->
                ExplorarViewModel(container.ubicacionRepository)
            else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
        }
        return viewModel as T
    }
}
