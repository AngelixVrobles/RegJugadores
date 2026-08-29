package com.angelixvasquez.regjugadores.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.angelixvasquez.regjugadores.ui.*

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
            composable(Screen.Lista.route) {
                ListaJugadoresScreen(
                    viewModel = jugadorViewModel,
                    onAgregarClick = { navController.navigate(Screen.Registro.crearRuta()) },
                    onEditarClick = { id -> navController.navigate(Screen.Registro.crearRuta(id)) },
                    onJuegoClick = { navController.navigate(Screen.Seleccion.route) },
                    onHistorialClick = { navController.navigate(Screen.Partidas.route) },
                    onLogrosClick = { id -> navController.navigate(Screen.Logros.crearRuta(id)) }
                )
            }

            composable(Screen.Registro.route) { backStackEntry ->
                val jugadorId = backStackEntry.arguments?.getString("jugadorId")?.toIntOrNull() ?: -1
                RegistroJugadorScreen(
                    viewModel = jugadorViewModel,
                    jugadorId = jugadorId,
                    onGuardar = { navController.popBackStack() }
                )
            }

            composable(Screen.Seleccion.route) {
                SeleccionJugadoresScreen(
                    viewModel = jugadorViewModel,
                    onJugarClick = { j1Id, j1, j2Id, j2 ->
                        navController.navigate(Screen.Juego.crearRuta(j1Id, j1, j2Id, j2))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

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

            composable(Screen.Partidas.route) {
                ListaPartidasScreen(
                    viewModel = partidaViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // ✅ Logros con botón FAB para agregar
            composable(Screen.Logros.route) { backStackEntry ->
                val jugadorId = backStackEntry.arguments?.getString("jugadorId")?.toIntOrNull() ?: -1
                ListaLogrosScreen(
                    viewModel = logroViewModel,
                    jugadorId = jugadorId,
                    onBack = { navController.popBackStack() },
                    onAgregarClick = { navController.navigate(Screen.RegistroLogro.crearRuta(jugadorId)) }
                )
            }

            // ✅ Registro de logros
            composable(Screen.RegistroLogro.route) { backStackEntry ->
                val jugadorId = backStackEntry.arguments?.getString("jugadorId")?.toIntOrNull() ?: -1
                RegistroLogroScreen(
                    viewModel = logroViewModel,
                    jugadorId = jugadorId,
                    onGuardar = { navController.popBackStack() }
                )
            }
        }
    }
}
