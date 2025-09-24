package com.example.regjugadores.navigation

sealed class Screen(val route: String) {
    object Lista : Screen("lista_jugadores")

    object Registro : Screen("registro_jugador?jugadorId={jugadorId}") {
        fun crearRuta(jugadorId: Int? = null): String {
            return if (jugadorId != null) "registro_jugador?jugadorId=$jugadorId"
            else "registro_jugador?jugadorId=-1"
        }
    }

    object Seleccion : Screen("seleccion_jugadores")

    object Juego : Screen("tic_tac_toe/{jugador1Id}/{jugador1}/{jugador2Id}/{jugador2}") {
        fun crearRuta(j1Id: Int, j1: String, j2Id: Int, j2: String): String {
            return "tic_tac_toe/$j1Id/$j1/$j2Id/$j2"
        }
    }

    object Partidas : Screen("lista_partidas")

    // 👇 Nueva pantalla de logros
    object Logros : Screen("logros/{jugadorId}") {
        fun crearRuta(jugadorId: Int): String = "logros/$jugadorId"
    }

    object RegistroLogro : Screen("registro_logro/{jugadorId}") {
        fun crearRuta(jugadorId: Int): String = "registro_logro/$jugadorId"
    }
}
