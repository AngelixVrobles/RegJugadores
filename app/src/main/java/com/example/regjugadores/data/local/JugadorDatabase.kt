package com.example.regjugadores.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Jugador::class,
        Partida::class,
        Logro::class
    ],
    version = 4,
    exportSchema = false
)
abstract class JugadorDatabase : RoomDatabase() {

    abstract fun jugadorDao(): JugadorDao
    abstract fun partidaDao(): PartidaDao
    abstract fun logroDao(): LogroDao

    companion object {
        @Volatile
        private var INSTANCE: JugadorDatabase? = null

        fun getDatabase(context: Context): JugadorDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JugadorDatabase::class.java,
                    "jugador_db"
                )

                    .fallbackToDestructiveMigration()

                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
