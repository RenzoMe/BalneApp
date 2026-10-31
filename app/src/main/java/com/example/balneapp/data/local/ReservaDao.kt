package com.example.balneapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {
    @Transaction
    @Query("SELECT * FROM reservas ORDER BY fechaCreacion DESC")
    fun observarTodas(): Flow<List<ReservaConEquipos>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarReserva(reserva: ReservaEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertarEquipos(equipos: List<EquipoReservaEntity>)

    @Transaction
    suspend fun insertarCompleta(reserva: ReservaEntity, equipos: List<EquipoReservaEntity>) {
        insertarReserva(reserva)
        insertarEquipos(equipos)
    }
}
