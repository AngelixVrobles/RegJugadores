package com.example.regjugadores.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(jugador: Jugador)

    @Query("SELECT * FROM jugadores ORDER BY jugadorId DESC")
    fun obtenerTodos(): Flow<List<Jugador>>

    @Update
    suspend fun editar(jugador: Jugador)

    @Delete
    suspend fun eliminar(jugador: Jugador)
}