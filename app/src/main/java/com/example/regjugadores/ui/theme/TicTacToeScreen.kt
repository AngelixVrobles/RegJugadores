package com.example.regjugadores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeScreen(
    jugador1: String,
    jugador2: String,
    onBack: () -> Unit
) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf(jugador1) }
    var winner by remember { mutableStateOf<String?>(null) }
    var empate by remember { mutableStateOf(false) }

    fun checkWinner(): String? {
        val winningPositions = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // filas
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // columnas
            listOf(0, 4, 8), listOf(2, 4, 6)                   // diagonales
        )
        for (pos in winningPositions) {
            if (board[pos[0]].isNotEmpty() &&
                board[pos[0]] == board[pos[1]] &&
                board[pos[1]] == board[pos[2]]
            ) {
                return board[pos[0]]
            }
        }
        return null
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tic Tac Toe", fontSize = 28.sp, modifier = Modifier.padding(16.dp))

        when {
            winner != null -> Text("Ganador: $winner 🎉", fontSize = 20.sp, color = Color.Green)
            empate -> Text("¡Empate! 🤝", fontSize = 20.sp, color = Color.Blue)
            else -> Text("Turno de: $currentPlayer", fontSize = 20.sp)
        }

        Spacer(Modifier.height(24.dp))

        // ✅ Tablero centrado
        Column(
            modifier = Modifier.weight(1f), // Ocupa el espacio disponible
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (row in 0..2) {
                Row {
                    for (col in 0..2) {
                        val index = row * 3 + col
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                                .background(Color.LightGray)
                                .clickable(enabled = board[index].isEmpty() && winner == null && !empate) {
                                    board = board.toMutableList().also {
                                        it[index] = if (currentPlayer == jugador1) "X" else "O"
                                    }

                                    val posibleGanador = checkWinner()
                                    if (posibleGanador != null) {
                                        winner = if (posibleGanador == "X") jugador1 else jugador2
                                    } else if (board.none { it.isEmpty() }) {
                                        empate = true
                                    } else {
                                        currentPlayer = if (currentPlayer == jugador1) jugador2 else jugador1
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(board[index], fontSize = 32.sp)
                        }
                    }
                }
            }
        }

        // ✅ Botón Reiniciar encima del de Volver
        Button(
            onClick = {
                board = List(9) { "" }
                currentPlayer = jugador1
                winner = null
                empate = false
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Reiniciar")
        }

        // ✅ Botón Volver al final de la pantalla
        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
