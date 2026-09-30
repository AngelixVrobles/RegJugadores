package com.angelixvasquez.regjugadores.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Jugador::class,
        Partida::class,
        Logro::class // 👈 añadimos la entidad Logro
    ],
    version = 4, // 👈 incrementa cada vez que cambies entidades
    exportSchema = false
)
abstract class JugadorDatabase : RoomDatabase() {

    abstract fun jugadorDao(): JugadorDao
    abstract fun partidaDao(): PartidaDao
    abstract fun logroDao(): LogroDao // 👈 nuevo DAO

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
                    .fallbackToDestructiveMigration() // 👈 elimina DB vieja si cambian entidades
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
