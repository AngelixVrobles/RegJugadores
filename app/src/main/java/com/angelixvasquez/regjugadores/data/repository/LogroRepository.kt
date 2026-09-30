package com.angelixvasquez.regjugadores.data.repository

import com.angelixvasquez.regjugadores.data.local.Logro
import com.angelixvasquez.regjugadores.data.local.LogroDao
import kotlinx.coroutines.flow.Flow

class LogroRepository(private val dao: LogroDao) {
    suspend fun insertar(logro: Logro) = dao.insertar(logro)
    fun obtenerLogrosPorJugador(jugadorId: Int): Flow<List<Logro>> = dao.obtenerLogrosPorJugador(jugadorId)
    suspend fun eliminar(id: Int) = dao.eliminar(id)
}
