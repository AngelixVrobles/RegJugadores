package com.example.regjugadores.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.regjugadores.ui.*

@Composable
fun AppNavHost(
    navController: NavHostController,
    jugadorViewModel: JugadorViewModel,
    partidaViewModel: PartidaViewModel,
    logroViewModel: LogroViewModel
) {
    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Lista.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 🧩 Lista de jugadores
            composable(Screen.Lista.route) {
                ListaJugadoresScreen(
                    viewModel = jugadorViewModel,
                    onAgregarClick = { navController.navigate(Screen.Registro.crearRuta()) },
                    onEditarClick = { id -> navController.navigate(Screen.Registro.crearRuta(id)) },
                    onJuegoClick = { navController.navigate(Screen.Seleccion.route) },
                    onHistorialClick = { navController.navigate(Screen.Partidas.route) }
                    // ❌ se eliminó el onLogrosClick (no existe en esa pantalla)
                )
            }

            // 🧩 Registro de jugador
            composable(Screen.Registro.route) { backStackEntry ->
                val jugadorId =
                    backStackEntry.arguments?.getString("jugadorId")?.toIntOrNull() ?: -1
                RegistroJugadorScreen(
                    viewModel = jugadorViewModel,
                    jugadorId = jugadorId,
                    onGuardar = { navController.popBackStack() }
                )
            }

            // 🧩 Selección de jugadores
            composable(Screen.Seleccion.route) {
                SeleccionJugadoresScreen(
                    viewModel = jugadorViewModel,
                    onJugarClick = { j1Id, j1, j2Id, j2 ->
                        navController.navigate(Screen.Juego.crearRuta(j1Id, j1, j2Id, j2))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // 🧩 Juego (Tic Tac Toe)
            composable(Screen.Juego.route) { backStackEntry ->
                val j1Id = backStackEntry.arguments?.getString("jugador1Id")?.toIntOrNull() ?: -1
                val j1 = backStackEntry.arguments?.getString("jugador1") ?: ""
                val j2Id = backStackEntry.arguments?.getString("jugador2Id")?.toIntOrNull() ?: -1
                val j2 = backStackEntry.arguments?.getString("jugador2") ?: ""

                TicTacToeScreen(
                    jugador1Id = j1Id,
                    jugador1 = j1,
                    jugador2Id = j2Id,
                    jugador2 = j2,
                    partidaViewModel = partidaViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 🧩 Lista de partidas
            composable(Screen.Partidas.route) {
                ListaPartidasScreen(
                    viewModel = partidaViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 🧩 Lista general de logros (gestión completa)
            composable(Screen.Logros.route) {
                ListaLogrosScreen(
                    viewModel = logroViewModel,
                    jugadorId = -1, // 👈 general, no jugador específico
                    onBack = { navController.popBackStack() },
                    onAgregarClick = {
                        navController.navigate("registro_logro/-1")
                    }
                )
            }

            // 🧩 Registro de un logro
            composable("registro_logro/{jugadorId}") { backStackEntry ->
                val jugadorId =
                    backStackEntry.arguments?.getString("jugadorId")?.toIntOrNull() ?: -1
                RegistroLogroScreen(
                    viewModel = logroViewModel,
                    jugadorId = jugadorId,
                    onGuardar = { navController.popBackStack() }
                )
            }
        }
    }
}
