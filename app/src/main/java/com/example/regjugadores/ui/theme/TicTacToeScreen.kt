package com.example.regjugadores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun TicTacToeScreen(
    jugador1: String,
    jugador2: String,
    jugador1Id: Int,
    jugador2Id: Int,
    partidaViewModel: PartidaViewModel,
    onBack: () -> Unit
) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }
    var gameFinished by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    fun checkWinner(): String? {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        for (pattern in winPatterns) {
            val (a, b, c) = pattern
            if (board[a].isNotEmpty() && board[a] == board[b] && board[a] == board[c]) {
                return board[a]
            }
        }
        return null
    }

    fun isBoardFull(): Boolean = board.all { it.isNotEmpty() }

    fun handleMove(index: Int) {
        if (board[index].isEmpty() && !gameFinished) {
            board = board.toMutableList().apply { this[index] = currentPlayer }
            winner = checkWinner()

            if (winner != null) {
                // ✅ Guardar ganador
                scope.launch {
                    partidaViewModel.registrarPartida(
                        jugador1Id = jugador1Id,
                        jugador2Id = jugador2Id,
                        ganadorId = if (winner == "X") jugador1Id else jugador2Id
                    )
                }
                gameFinished = true
            } else if (isBoardFull()) {
                // ✅ Guardar empate
                scope.launch {
                    partidaViewModel.registrarPartida(
                        jugador1Id = jugador1Id,
                        jugador2Id = jugador2Id,
                        ganadorId = null
                    )
                }
                gameFinished = true
            }

            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    // --- UI ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Jugador X: $jugador1")
        Text("Jugador O: $jugador2")

        Column {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        val index = i * 3 + j
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(4.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .clickable { handleMove(index) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(board[index], fontSize = 32.sp)
                        }
                    }
                }
            }
        }

        Text(
            when {
                winner != null -> "🏆 Ganador: ${if (winner == "X") jugador1 else jugador2}"
                gameFinished -> "🤝 Empate"
                else -> "Turno: $currentPlayer"
            },
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}

