package com.example.regjugadores.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.regjugadores.data.local.Jugador

@Composable
fun SeleccionJugadoresScreen(
    viewModel: JugadorViewModel,
    onJugarClick: (Int, String, Int, String) -> Unit, // ✅ IDs + nombres
    onBack: () -> Unit
) {
    val jugadores by viewModel.jugadores.collectAsState()

    var jugadorX by remember { mutableStateOf<Jugador?>(null) }
    var jugadorO by remember { mutableStateOf<Jugador?>(null) }

    var mostrarDialogoX by remember { mutableStateOf(false) }
    var mostrarDialogoO by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // 📌 Título justo encima de los botones
        Text("Selecciona Jugadores", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Botones Jugador X y Jugador O
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { mostrarDialogoX = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(jugadorX?.nombreJugador ?: "Jugador X")
            }

            Button(
                onClick = { mostrarDialogoO = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(jugadorO?.nombreJugador ?: "Jugador O")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 📌 Botón Iniciar Juego
        Button(
            onClick = {
                if (jugadorX != null && jugadorO != null) {
                    onJugarClick(
                        jugadorX!!.jugadorId, jugadorX!!.nombreJugador,
                        jugadorO!!.jugadorId, jugadorO!!.nombreJugador
                    )
                }
            },
            enabled = (jugadorX != null && jugadorO != null),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Iniciar Juego 🎮")
        }

        Spacer(modifier = Modifier.weight(1f))

        // 📌 Botón Volver
        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }

    // ✅ Diálogo Jugador X
    if (mostrarDialogoX) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoX = false },
            title = { Text("Selecciona Jugador X") },
            text = {
                Column {
                    jugadores.filter { it != jugadorO }.forEach { jugador ->
                        Text(
                            text = jugador.nombreJugador,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    jugadorX = jugador
                                    mostrarDialogoX = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }

    // ✅ Diálogo Jugador O
    if (mostrarDialogoO) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoO = false },
            title = { Text("Selecciona Jugador O") },
            text = {
                Column {
                    jugadores.filter { it != jugadorX }.forEach { jugador ->
                        Text(
                            text = jugador.nombreJugador,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    jugadorO = jugador
                                    mostrarDialogoO = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }
}
