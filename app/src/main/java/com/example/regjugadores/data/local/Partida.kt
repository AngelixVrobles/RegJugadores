package com.example.regjugadores.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class Partida(
    @PrimaryKey(autoGenerate = true) val partidaId: Int = 0,
    val fecha: Long,
    val jugador1Id: Int,
    val jugador2Id: Int,
    val ganadorId: Int?,  // null si es empate
    val esFinalizada: Boolean
)