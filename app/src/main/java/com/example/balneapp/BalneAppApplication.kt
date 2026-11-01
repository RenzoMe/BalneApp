package com.example.balneapp

import android.app.Application
import androidx.room.Room
import com.example.balneapp.data.local.BalneAppDatabase
import com.example.balneapp.data.repository.FavoritosRepository
import com.example.balneapp.data.repository.PreferenciasUsuarioRepository
import com.example.balneapp.data.repository.ReservasRepository
import com.example.balneapp.data.repository.UbicacionRepository
import com.example.balneapp.ui.viewmodel.BalneAppViewModelFactory

class BalneAppApplication : Application() {
    val container: AppContainer by lazy { AppContainer(this) }
    val viewModelFactory: BalneAppViewModelFactory by lazy { BalneAppViewModelFactory(container) }
}

class AppContainer(application: Application) {
    private val database: BalneAppDatabase by lazy {
        Room.databaseBuilder(application, BalneAppDatabase::class.java, "balneapp.db").build()
    }

    val favoritosRepository: FavoritosRepository by lazy { FavoritosRepository(database.favoritoDao()) }
    val reservasRepository: ReservasRepository by lazy { ReservasRepository(database.reservaDao()) }
    val preferenciasRepository: PreferenciasUsuarioRepository by lazy { PreferenciasUsuarioRepository(application) }
    val ubicacionRepository: UbicacionRepository by lazy { UbicacionRepository(application) }
}
