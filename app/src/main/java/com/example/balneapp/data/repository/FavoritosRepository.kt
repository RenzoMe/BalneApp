package com.example.balneapp.data.repository

import com.example.balneapp.data.local.FavoritoDao
import com.example.balneapp.data.local.FavoritoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritosRepository(private val dao: FavoritoDao) {
    val idsFavoritos: Flow<Set<String>> = dao.observarIds().map { it.toSet() }

    suspend fun alternar(id: String) {
        if (dao.existe(id)) dao.eliminar(id) else dao.insertar(FavoritoEntity(id))
    }

    suspend fun quitar(id: String) = dao.eliminar(id)
}
