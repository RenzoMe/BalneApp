package com.example.balneapp.ui.model

import java.time.YearMonth

enum class EquipamientoReserva(val etiqueta: String) {
    CARPAS("Carpas"),
    SOMBRILLAS("Sombrillas")
}

data class ReservasUiState(
    val mes: YearMonth = YearMonth.of(2026, 5),
    val diasSeleccionados: Set<Int> = setOf(23, 24, 25, 26),
    val primerDiaSeleccionado: Int? = null,
    val personas: Int = 2,
    val equipamiento: Set<EquipamientoReserva> = setOf(
        EquipamientoReserva.CARPAS,
        EquipamientoReserva.SOMBRILLAS
    ),
    val mostrarCalendarioInteractivo: Boolean = false,
    val mensaje: String? = null
) {
    // Precio de referencia que aparece en el frame de Figma.
    val total: Int = 25_000
}
