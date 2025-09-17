package com.example.regjugadores.data.repository

import com.example.regjugadores.data.local.Partida
import com.example.regjugadores.data.local.PartidaConJugadores
import com.example.regjugadores.data.local.PartidaDao

class PartidaRepository(private val dao: PartidaDao) {
    suspend fun insertar(partida: Partida) = dao.insertar(partida)
    suspend fun obtenerConJugadores(): List<PartidaConJugadores> = dao.obtenerConJugadores()
    suspend fun eliminar(partida: Partida) = dao.eliminar(partida)
}
