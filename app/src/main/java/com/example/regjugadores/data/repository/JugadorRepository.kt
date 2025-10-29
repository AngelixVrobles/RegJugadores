package com.example.regjugadores.data.repository

import com.example.regjugadores.data.local.Jugador
import com.example.regjugadores.data.local.JugadorDao
import com.example.regjugadores.data.remote.JugadorApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class JugadorRepository(
    private val dao: JugadorDao,
    private val api: JugadorApi
) {

    // 🔹 Obtener jugadores (Offline First)
    fun obtenerTodos(): Flow<List<Jugador>> = flow {
        val local = dao.obtenerTodos().first()
        emit(local)

        try {
            val remote = api.getJugadores()
            val entities = remote.map {
                Jugador(
                    jugadorId = it.jugadorId,
                    nombreJugador = it.nombres,
                    email = it.email
                )
            }
            entities.forEach { dao.insertar(it) }
            emit(dao.obtenerTodos().first())
        } catch (e: Exception) {
            emit(local)
        }
    }

    // 🔹 Insertar jugador
    suspend fun insertar(jugador: Jugador) {
        dao.insertar(jugador)
        try {
            api.postJugador(
                com.example.regjugadores.data.remote.dto.JugadorDto(
                    jugadorId = jugador.jugadorId,
                    nombres = jugador.nombreJugador,
                    email = jugador.email
                )
            )
        } catch (_: Exception) { }
    }

    // 🔹 Editar jugador
    suspend fun editar(jugador: Jugador) {
        dao.editar(jugador)
        try {
            api.putJugador(
                jugador.jugadorId,
                com.example.regjugadores.data.remote.dto.JugadorDto(
                    jugadorId = jugador.jugadorId,
                    nombres = jugador.nombreJugador,
                    email = jugador.email
                )
            )
        } catch (_: Exception) { }
    }

    // 🔹 Eliminar jugador
    suspend fun eliminar(jugador: Jugador) {
        dao.eliminar(jugador)
        try {
            api.deleteJugador(jugador.jugadorId)
        } catch (_: Exception) { }
    }
}
