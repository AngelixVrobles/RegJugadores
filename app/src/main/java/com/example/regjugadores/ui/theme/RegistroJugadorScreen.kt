package com.example.regjugadores.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.runtime.livedata.observeAsState
import com.example.regjugadores.data.local.Jugador

@Composable
fun RegistroJugadorScreen(viewModel: JugadorViewModel) {
    var nombre by remember { mutableStateOf("") }
    var partidas by remember { mutableStateOf("") }

    // ✅ Cargar jugadores apenas se abre la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarJugadores()
    }

    val jugadores: List<Jugador> by viewModel.jugadores.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro de Jugadores",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del jugador") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = partidas,
            onValueChange = { partidas = it },
            label = { Text("Partidas jugadas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (nombre.isNotBlank() && partidas.isNotEmpty()) {
                    viewModel.registrarJugador(nombre.trim(), partidas.toInt())
                    nombre = ""
                    partidas = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (viewModel.estaEditando()) "Editar jugador" else "Registrar jugador")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Jugadores Registrados:",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)
        )

        jugadores.forEach { jugador ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(jugador.nombres, style = MaterialTheme.typography.titleMedium)
                        Text("${jugador.partidas} partidas", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row {
                        // Botón Editar
                        IconButton(onClick = {
                            nombre = jugador.nombres
                            partidas = jugador.partidas.toString()
                            viewModel.setJugadorEditando(jugador)
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }

                        // Botón Eliminar
                        IconButton(onClick = { viewModel.eliminarJugador(jugador) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
