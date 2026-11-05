package com.example.balneapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritoDao {
    @Query("SELECT balnearioId FROM favoritos")
    fun observarIds(): Flow<List<String>>

    @Query("SELECT EXISTS(SELECT 1 FROM favoritos WHERE balnearioId = :id)")
    suspend fun existe(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(favorito: FavoritoEntity)

    @Query("DELETE FROM favoritos WHERE balnearioId = :id")
    suspend fun eliminar(id: String)
}
