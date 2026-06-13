package com.angelixvasquez.regjugadores.data.repository

import com.angelixvasquez.regjugadores.data.local.Partida
import com.angelixvasquez.regjugadores.data.local.PartidaConJugadores
import com.angelixvasquez.regjugadores.data.local.PartidaDao

class PartidaRepository(private val dao: PartidaDao) {
    suspend fun insertar(partida: Partida) = dao.insertar(partida)
    suspend fun obtenerConJugadores(): List<PartidaConJugadores> = dao.obtenerConJugadores()
    suspend fun eliminar(partida: Partida) = dao.eliminar(partida)
}
