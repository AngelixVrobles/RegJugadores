package com.example.regjugadores.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class Partida(
    @PrimaryKey(autoGenerate = true) val partidaId: Int = 0,
    val fecha: Long = System.currentTimeMillis(), // guardamos en timestamp
    val jugador1Id: Int,
    val jugador2Id: Int,
    val ganadorId: Int?, // puede ser null si hay empate
    val esFinalizada: Boolean = false
)
