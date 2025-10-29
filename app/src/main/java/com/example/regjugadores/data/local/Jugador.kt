package com.example.regjugadores.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class Jugador(
    @PrimaryKey(autoGenerate = true)
    val jugadorId: Int = 0,
    val nombreJugador: String,
    val email: String
)
