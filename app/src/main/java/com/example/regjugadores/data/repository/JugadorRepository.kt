package com.example.regjugadores.data.repository

import com.example.regjugadores.data.local.Jugador
import com.example.regjugadores.data.local.JugadorDao
import kotlinx.coroutines.flow.Flow

class JugadorRepository(private val dao: JugadorDao) {
    suspend fun insertar(jugador: Jugador) = dao.insertar(jugador)
    fun obtenerTodos(): Flow<List<Jugador>> = dao.obtenerTodos()
    suspend fun editar(jugador: Jugador) = dao.editar(jugador)
    suspend fun eliminar(jugador: Jugador) = dao.eliminar(jugador)
}