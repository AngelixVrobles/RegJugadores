package com.example.regjugadores.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ListaJugadoresScreen(
    viewModel: JugadorViewModel,
    onAgregarClick: () -> Unit,
    onEditarClick: (Int) -> Unit,
    onJuegoClick: () -> Unit,
    onHistorialClick: () -> Unit // nuevo parámetro
) {
    val jugadores by viewModel.jugadores.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAgregarClick() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar jugador")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Título centrado
            Text(
                text = "Jugadores Registrados",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            // Botón para ir al juego
            Button(
                onClick = { onJuegoClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("Ir al juego de TicTacToe 🎮")
            }

            // Botón para ver historial de partidas
            Button(
                onClick = { onHistorialClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Ver historial de partidas 📜")
            }

            if (jugadores.isEmpty()) {
                // Texto centrado cuando no hay jugadores
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay jugadores registrados",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                jugadores.forEach { jugador ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    jugador.nombreJugador,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    "${jugador.partidasJugadas} partidas",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Row {
                                IconButton(onClick = { onEditarClick(jugador.jugadorId) }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = "Editar",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                IconButton(onClick = { viewModel.eliminarJugador(jugador) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
