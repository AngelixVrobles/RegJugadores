package com.example.regjugadores.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.regjugadores.ui.JugadorViewModel
import com.example.regjugadores.ui.ListaJugadoresScreen
import com.example.regjugadores.ui.ListaPartidasScreen
import com.example.regjugadores.ui.PartidaViewModel
import com.example.regjugadores.ui.RegistroJugadorScreen
import com.example.regjugadores.ui.SeleccionJugadoresScreen
import com.example.regjugadores.ui.TicTacToeScreen

@Composable
fun AppNavHost(
    jugadorViewModel: JugadorViewModel,
    partidaViewModel: PartidaViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Lista.route
    ) {
        // 📌 Lista de jugadores
        composable(Screen.Lista.route) {
            ListaJugadoresScreen(
                viewModel = jugadorViewModel,
                onAgregarClick = { navController.navigate(Screen.Registro.crearRuta()) },
                onEditarClick = { jugadorId: Int ->
                    navController.navigate(Screen.Registro.crearRuta(jugadorId))
                },
                onJuegoClick = { navController.navigate(Screen.Seleccion.route) },
                onHistorialClick = { navController.navigate(Screen.Partidas.route) }
            )
        }

        // 📌 Registro / edición de jugadores
        composable(
            route = Screen.Registro.route,
            arguments = listOf(navArgument("jugadorId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val jugadorId: Int = backStackEntry.arguments?.getInt("jugadorId") ?: -1
            RegistroJugadorScreen(
                viewModel = jugadorViewModel,
                jugadorId = jugadorId,
                onGuardar = { navController.popBackStack() }
            )
        }

        // 📌 Selección de jugadores antes de jugar
        composable(Screen.Seleccion.route) {
            SeleccionJugadoresScreen(
                viewModel = jugadorViewModel,
                onJugarClick = { j1: String, j2: String ->
                    navController.navigate(Screen.Juego.crearRuta(j1, j2))
                },
                onBack = { navController.popBackStack() }
            )
        }

        // 📌 Juego TicTacToe
        composable(
            route = Screen.Juego.route,
            arguments = listOf(
                navArgument("jugador1") { type = NavType.StringType },
                navArgument("jugador2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val j1: String = backStackEntry.arguments?.getString("jugador1") ?: "Jugador 1"
            val j2: String = backStackEntry.arguments?.getString("jugador2") ?: "Jugador 2"

            TicTacToeScreen(
                jugador1 = j1,
                jugador2 = j2,
                onBack = { navController.popBackStack() }
            )
        }

        // 📌 Historial de partidas
        composable(Screen.Partidas.route) {
            ListaPartidasScreen(
                viewModel = partidaViewModel,
                onAgregarClick = {
                    // Aquí pones la lógica si quieres agregar partida manual
                }
            )
        }
    }
}
