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

    object Juego : Screen("tic_tac_toe/{jugador1}/{jugador2}") {
        fun crearRuta(j1: String, j2: String) = "tic_tac_toe/$j1/$j2"
    }

    object Partidas : Screen("lista_partidas")
}
