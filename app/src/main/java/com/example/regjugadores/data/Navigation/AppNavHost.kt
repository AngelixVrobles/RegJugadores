package com.example.regjugadores.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.regjugadores.ui.ListaJugadoresScreen
import com.example.regjugadores.ui.RegistroJugadorScreen
import com.example.regjugadores.ui.JugadorViewModel
import com.example.regjugadores.ui.SeleccionJugadoresScreen
import com.example.regjugadores.ui.TicTacToeScreen

@Composable
fun AppNavHost(viewModel: JugadorViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Lista.route
    ) {
        // 📌 Lista de jugadores
        composable(Screen.Lista.route) {
            ListaJugadoresScreen(
                viewModel = viewModel,
                onAgregarClick = { navController.navigate(Screen.Registro.crearRuta()) },
                onEditarClick = { jugadorId: Int -> // 👈 tipo explícito
                    navController.navigate(Screen.Registro.crearRuta(jugadorId))
                },
                onJuegoClick = { navController.navigate(Screen.Seleccion.route) } // 👈 navega a selección
            )
        }

        // 📌 Registro / edición
        composable(
            route = Screen.Registro.route,
            arguments = listOf(navArgument("jugadorId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val jugadorId: Int = backStackEntry.arguments?.getInt("jugadorId") ?: -1
            RegistroJugadorScreen(
                viewModel = viewModel,
                jugadorId = jugadorId,
                onGuardar = { navController.popBackStack() }
            )
        }

        // 📌 Selección de jugadores antes de jugar
        composable(Screen.Seleccion.route) {
            SeleccionJugadoresScreen(
                viewModel = viewModel,
                onJugarClick = { j1: String, j2: String -> // 👈 tipos explícitos
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
    }
}
