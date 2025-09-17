package com.example.regjugadores.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun CustomBottomBar(navController: NavHostController) {
    val items = listOf(
        Screen.Lista,     // 🏠 Jugadores Registrados
        Screen.Registro,  // ➕ Registrar jugador
        Screen.Seleccion, // 🎮 Selección de jugadores
        Screen.Partidas   // 📜 Historial de partidas
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                icon = {
                    when (screen) {
                        is Screen.Lista -> Icon(
                            Icons.Filled.Home,
                            contentDescription = "Jugadores"
                        )

                        is Screen.Registro -> Icon(
                            Icons.Filled.PersonAdd,
                            contentDescription = "Registrar"
                        )

                        is Screen.Seleccion -> Icon(
                            Icons.Filled.SportsEsports,
                            contentDescription = "Jugar"
                        )

                        is Screen.Partidas -> Icon(
                            Icons.Filled.History,
                            contentDescription = "Historial"
                        )
                        // 👇 Para evitar error de when exhaustivo
                        else -> Icon(
                            Icons.Filled.Home,
                            contentDescription = "Default"
                        )
                    }
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}
