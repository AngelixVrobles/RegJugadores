package com.angelixvasquez.regjugadores.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(logro: Logro)

    @Query("SELECT * FROM logros WHERE jugadorId = :jugadorId ORDER BY fecha DESC")
    fun obtenerLogrosPorJugador(jugadorId: Int): Flow<List<Logro>>

    @Query("DELETE FROM logros WHERE logroId = :id")
    suspend fun eliminar(id: Int)
}
