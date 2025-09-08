package com.example.regjugadores.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Jugador::class], version = 1)
abstract class JugadorDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
}