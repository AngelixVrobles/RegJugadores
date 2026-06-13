package com.angelixvasquez.regjugadores.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.angelixvasquez.regjugadores.data.local.Jugador

@Composable
fun RegistroJugadorScreen(
    viewModel: JugadorViewModel,
    jugadorId: Int,
    onGuardar: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var partidas by remember { mutableStateOf("") }

    // ✅ Si es edición, cargamos datos del jugador
    LaunchedEffect(jugadorId) {
        if (jugadorId != -1) {
            val jugador = viewModel.jugadores.value.find { it.jugadorId == jugadorId }
            jugador?.let {
                nombre = it.nombreJugador
                partidas = it.partidasJugadas.toString()
                viewModel.setJugadorEditando(it)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (jugadorId == -1) "Registrar Jugador" else "Editar Jugador",
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
                    onGuardar() // 🔙 volver a la lista
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (jugadorId == -1) "Registrar" else "Guardar cambios")
        }
    }
}
