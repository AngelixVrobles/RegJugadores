package com.example.regjugadores.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Jugador::class], version = 1, exportSchema = false)
abstract class JugadorDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao

    companion object {
        @Volatile
        private var INSTANCE: JugadorDatabase? = null

        fun getDatabase(context: Context): JugadorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JugadorDatabase::class.java,
                    "jugador_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
