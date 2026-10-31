package com.example.balneapp.data.repository

import com.example.balneapp.data.local.EquipoReservaEntity
import com.example.balneapp.data.local.ReservaConEquipos
import com.example.balneapp.data.local.ReservaDao
import com.example.balneapp.data.local.ReservaEntity
import com.example.balneapp.ui.model.BalnearioReservaInfo
import com.example.balneapp.ui.model.EquipoReserva
import com.example.balneapp.ui.model.ReservaBorrador
import com.example.balneapp.ui.model.ReservaConfirmada
import com.example.balneapp.ui.model.TipoServicioReserva
import java.time.LocalDate
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReservasRepository(private val dao: ReservaDao) {
    val reservas: Flow<List<ReservaConfirmada>> = dao.observarTodas().map { rows ->
        rows.map(ReservaConEquipos::toDomain)
    }

    suspend fun confirmar(borrador: ReservaBorrador): ReservaConfirmada {
        val id = UUID.randomUUID().toString()
        dao.insertarCompleta(
            reserva = ReservaEntity(
                id = id,
                balnearioId = borrador.balneario.id,
                fechaIso = borrador.fecha.toString(),
                tipoServicio = borrador.tipoServicio.name,
                personas = borrador.personas,
                precioTotal = borrador.total
            ),
            equipos = borrador.equipamiento.map { equipo ->
                EquipoReservaEntity(
                    reservaId = id,
                    nombre = equipo.nombre,
                    cantidad = equipo.cantidad,
                    precioPorUnidad = equipo.precioPorUnidad
                )
            }
        )
        return ReservaConfirmada(id, borrador)
    }
}

private fun ReservaConEquipos.toDomain(): ReservaConfirmada {
    val balneario = requireNotNull(BalnearioReservaInfo.buscar(reserva.balnearioId)) {
        "Balneario desconocido: ${reserva.balnearioId}"
    }
    val detalle = ReservaBorrador(
        balneario = balneario,
        fecha = LocalDate.parse(reserva.fechaIso),
        tipoServicio = TipoServicioReserva.valueOf(reserva.tipoServicio),
        equipamiento = equipos.map { EquipoReserva(it.nombre, it.cantidad, it.precioPorUnidad) },
        personas = reserva.personas
    )
    return ReservaConfirmada(reserva.id, detalle)
}
