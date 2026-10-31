package com.example.balneapp.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "reservas")
data class ReservaEntity(
    @PrimaryKey val id: String,
    val balnearioId: String,
    val fechaIso: String,
    val tipoServicio: String,
    val personas: Int,
    val precioTotal: Int,
    val fechaCreacion: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "equipos_reserva",
    foreignKeys = [
        ForeignKey(
            entity = ReservaEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("reservaId")]
)
data class EquipoReservaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reservaId: String,
    val nombre: String,
    val cantidad: Int,
    val precioPorUnidad: Int
)

data class ReservaConEquipos(
    @Embedded val reserva: ReservaEntity,
    @Relation(parentColumn = "id", entityColumn = "reservaId")
    val equipos: List<EquipoReservaEntity>
)
