package com.example.balneapp.ui.model

import com.example.balneapp.R
import java.time.LocalDate
import java.time.YearMonth

enum class TipoServicioReserva(val etiqueta: String) {
    CARPA("Carpa"),
    SOMBRILLA("Sombrilla")
}

data class OpcionEquipamientoReserva(
    val id: String,
    val nombre: String,
    val capacidad: String,
    val precioPorDia: Int,
    val tipoServicio: TipoServicioReserva
)

object CatalogoEquipamientoReserva {
    private val opciones = listOf(
        OpcionEquipamientoReserva("carpa-estandar", "Carpa estándar", "Hasta 4 personas", 25_000, TipoServicioReserva.CARPA),
        OpcionEquipamientoReserva("carpa-premium", "Carpa premium", "Hasta 6 personas", 35_000, TipoServicioReserva.CARPA),
        OpcionEquipamientoReserva("carpa-vip", "Carpa vip", "Hasta 8 personas", 45_000, TipoServicioReserva.CARPA),
        OpcionEquipamientoReserva("sombrilla-estandar", "Sombrilla estándar", "Hasta 4 personas", 15_000, TipoServicioReserva.SOMBRILLA),
        OpcionEquipamientoReserva("sombrilla-premium", "Sombrilla premium", "Hasta 6 personas", 25_000, TipoServicioReserva.SOMBRILLA),
        OpcionEquipamientoReserva("sombrilla-vip", "Sombrilla vip", "Hasta 8 personas", 35_000, TipoServicioReserva.SOMBRILLA)
    )

    fun para(tipoServicio: TipoServicioReserva): List<OpcionEquipamientoReserva> =
        opciones.filter { it.tipoServicio == tipoServicio }

    fun todas(): List<OpcionEquipamientoReserva> = opciones
}

data class BalnearioReservaInfo(
    val id: String,
    val nombre: String,
    val direccion: String,
    val puntuacion: Double,
    val cantidadResenas: Int,
    val imagenResId: Int
)

object CatalogoBalneariosReserva {
    private val balnearios = listOf(
        BalnearioReservaInfo(
            id = BalnearioIds.GOLONDRINAS,
            nombre = "Balneario Golondrinas",
            direccion = "Av. costanera y 33",
            puntuacion = 4.4,
            cantidadResenas = 134,
            imagenResId = R.drawable.imagengolondrina
        ),
        BalnearioReservaInfo(
            id = BalnearioIds.OASIS,
            nombre = "Balneario Oasis",
            direccion = "Av. costanera y 27",
            puntuacion = 3.8,
            cantidadResenas = 103,
            imagenResId = R.drawable.img_oasis
        )
    )

    fun buscar(id: String): BalnearioReservaInfo? = balnearios.firstOrNull { it.id == id }
}

data class EquipoReserva(
    val nombre: String,
    val cantidad: Int,
    val precioPorUnidad: Int
)

data class ReservaBorrador(
    val balneario: BalnearioReservaInfo,
    val fecha: LocalDate,
    val tipoServicio: TipoServicioReserva,
    val equipamiento: List<EquipoReserva>,
    val personas: Int
) {
    val precioPorDia: Int = equipamiento.sumOf { it.cantidad * it.precioPorUnidad }
    val total: Int = precioPorDia
    val servicioResumen: String = equipamiento.joinToString { equipo ->
        if (equipo.cantidad == 1) equipo.nombre else "${equipo.nombre} x${equipo.cantidad}"
    }
}

data class ReservaConfirmada(
    val id: String,
    val detalle: ReservaBorrador
)

private val primeraFechaDisponible = LocalDate.now().plusDays(1)

data class DisponibilidadUiState(
    val mesVisible: YearMonth = YearMonth.from(primeraFechaDisponible),
    val fechaSeleccionada: LocalDate = primeraFechaDisponible,
    val tipoServicio: TipoServicioReserva = TipoServicioReserva.CARPA,
    val cantidades: Map<String, Int> = CatalogoEquipamientoReserva.todas().associate { opcion ->
        opcion.id to if (opcion.id == "carpa-estandar" || opcion.id == "sombrilla-estandar") 1 else 0
    },
    val mensaje: String? = null
) {
    val opcionesDisponibles: List<OpcionEquipamientoReserva>
        get() = CatalogoEquipamientoReserva.para(tipoServicio)

    val totalPorDia: Int
        get() = opcionesDisponibles.sumOf { opcion ->
            opcion.precioPorDia * (cantidades[opcion.id] ?: 0)
        }
}
