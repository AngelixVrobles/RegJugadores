package com.example.regjugadores.data.repository

import com.example.regjugadores.data.local.Jugador
import com.example.regjugadores.data.local.JugadorDao

class JugadorRepository(private val dao: JugadorDao) {
    suspend fun insertar(jugador: Jugador) = dao.insertar(jugador)
    suspend fun obtenerTodos() = dao.obtenerTodos()
    suspend fun eliminar(jugador: Jugador) = dao.eliminar(jugador)
}
