package com.example.balneapp.navigation

/** Route catalog for the app's Navigation Compose graph. */
sealed class AppRoute(val route: String) {
    object Splash : AppRoute("inicio")
    object Login : AppRoute("logueo")
    object Registro : AppRoute("registro")

    object Inicio : AppRoute("principal")
    object Favoritos : AppRoute("favoritos")
    object Explorar : AppRoute("explorar")
    object Reservas : AppRoute("reservas")
    object Perfil : AppRoute("perfil")

    object MisDatos : AppRoute("misDatos")
    object MetodosPago : AppRoute("metodosPago")
    object Configuracion : AppRoute("configuracion")
    object Ayuda : AppRoute("ayuda")

    object Disponibilidad : AppRoute("disponibilidad/{balnearioId}") {
        const val argumentName = "balnearioId"

        fun conId(balnearioId: String): String = "disponibilidad/$balnearioId"
    }

    object ResumenReserva : AppRoute("resumenReserva")
    object Resenas : AppRoute("resenas")
    object Golondrinas : AppRoute("golondrinas")
    object Oasis : AppRoute("oasis")
}
