package com.example.regjugadores.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class Jugador(
    @PrimaryKey(autoGenerate = true) val jugadorId: Int = 0,
    @ColumnInfo(name = "nombre_jugador") val nombreJugador: String,
    @ColumnInfo(name = "partidas_jugadas") val partidasJugadas: Int = 0
)