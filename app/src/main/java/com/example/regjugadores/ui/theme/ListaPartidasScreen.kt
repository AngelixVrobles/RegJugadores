package com.example.regjugadores.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListaPartidasScreen(
    viewModel: PartidaViewModel,
    onAgregarClick: () -> Unit
) {
    val partidas by viewModel.partidasConJugadores.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 📌 Título centrado
            Text(
                "Historial de Partidas",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (partidas.isEmpty()) {
                Text(
                    text = "No hay partidas registradas",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            } else {
                partidas.forEach { partidaConJugadores ->
                    val partida = partidaConJugadores.partida
                    val j1 = partidaConJugadores.jugador1.nombreJugador
                    val j2 = partidaConJugadores.jugador2.nombreJugador
                    val ganador = partidaConJugadores.ganador?.nombreJugador ?: "Empate"

                    // 📌 Formatear fecha
                    val fechaStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        .format(Date(partida.fecha))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("📅 Fecha: $fechaStr")
                            Text("👤 Jugador 1: $j1")
                            Text("👤 Jugador 2: $j2")
                            Text("🏆 Ganador: $ganador")
                            Text("Estado: ${if (partida.esFinalizada) "Finalizada ✅" else "En curso ⏳"}")
                        }
                    }
                }
            }
        }
    }
}
