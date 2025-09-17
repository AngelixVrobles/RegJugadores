package com.example.regjugadores.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PartidaDao {
    // ✅ Insertar una partida
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(partida: Partida)

    // ✅ Obtener todas las partidas (solo datos de Partida, con IDs)
    @Query("SELECT * FROM partidas ORDER BY fecha DESC")
    suspend fun obtenerTodas(): List<Partida>

    // ✅ Obtener todas las partidas con jugadores (JOIN)
    @Transaction
    @Query("SELECT * FROM partidas ORDER BY fecha DESC")
    suspend fun obtenerConJugadores(): List<PartidaConJugadores>

    // ✅ Editar partida existente
    @Update
    suspend fun editar(partida: Partida)

    // ✅ Eliminar partida
    @Delete
    suspend fun eliminar(partida: Partida)
}
