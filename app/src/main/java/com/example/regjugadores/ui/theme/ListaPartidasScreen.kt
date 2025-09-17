package com.example.regjugadores.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.regjugadores.data.local.PartidaConJugadores
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class) // 👈 Esto quita el warning
@Composable
fun ListaPartidasScreen(
    viewModel: PartidaViewModel,
    onBack: () -> Unit // ✅ parámetro para navegar atrás
) {
    val partidas by viewModel.partidasConJugadores.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Historial de Partidas") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (partidas.isEmpty()) {
                Text(
                    text = "No hay partidas registradas",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                partidas.forEach { partidaConJugadores: PartidaConJugadores ->
                    val partida = partidaConJugadores.partida
                    val j1 = partidaConJugadores.jugador1.nombreJugador
                    val j2 = partidaConJugadores.jugador2.nombreJugador
                    val ganador = partidaConJugadores.ganador?.nombreJugador ?: "Empate"

                    val fechaStr = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
                        .format(Date(partida.fecha))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("📅 $fechaStr", style = MaterialTheme.typography.bodySmall)
                            Text("$j1 🆚 $j2", style = MaterialTheme.typography.bodyMedium)
                            Text("🏆 $ganador", style = MaterialTheme.typography.bodyLarge)

                            Text(
                                if (partida.esFinalizada) "Finalizada ✅" else "En curso ⏳",
                                style = MaterialTheme.typography.labelLarge,
                                color = if (partida.esFinalizada)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // 👈 Empuja el botón al fondo


        }
    }
}
