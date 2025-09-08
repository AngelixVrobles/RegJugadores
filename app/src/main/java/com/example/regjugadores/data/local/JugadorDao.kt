package com.example.regjugadores.data.local

import androidx.room.*

@Dao
interface JugadorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertar(jugador: Jugador)

    @Query("SELECT * FROM jugadores")
    suspend fun obtenerTodos(): List<Jugador>

    @Update
    suspend fun editar(jugador: Jugador) // 👈 antes era actualizar

    @Delete
    suspend fun eliminar(jugador: Jugador)
}