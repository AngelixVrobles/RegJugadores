package com.example.regjugadores.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Scaffold(
        bottomBar = { CustomBottomBar(navController) } // ✅ Barra inferior personalizada
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Lista.route, // 🏠 Jugadores Registrados
            modifier = Modifier.padding(innerPadding)
        ) {
            // 📌 Lista de jugadores (Home)
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
                    onJugarClick = { j1Id: Int, j1: String, j2Id: Int, j2: String ->
                        navController.navigate(Screen.Juego.crearRuta(j1Id, j1, j2Id, j2))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // 📌 Juego TicTacToe
            composable(
                route = Screen.Juego.route,
                arguments = listOf(
                    navArgument("jugador1Id") { type = NavType.IntType },
                    navArgument("jugador1") { type = NavType.StringType },
                    navArgument("jugador2Id") { type = NavType.IntType },
                    navArgument("jugador2") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val j1Id = backStackEntry.arguments?.getInt("jugador1Id") ?: -1
                val j1 = backStackEntry.arguments?.getString("jugador1") ?: "Jugador 1"
                val j2Id = backStackEntry.arguments?.getInt("jugador2Id") ?: -1
                val j2 = backStackEntry.arguments?.getString("jugador2") ?: "Jugador 2"

                TicTacToeScreen(
                    jugador1 = j1,
                    jugador2 = j2,
                    jugador1Id = j1Id,
                    jugador2Id = j2Id,
                    partidaViewModel = partidaViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 📌 Historial de partidas
            composable(Screen.Partidas.route) {
                ListaPartidasScreen(
                    viewModel = partidaViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
