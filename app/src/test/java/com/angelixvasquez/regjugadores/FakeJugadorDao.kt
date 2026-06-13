package com.angelixvasquez.regjugadores

import com.angelixvasquez.regjugadores.data.local.Jugador
import com.angelixvasquez.regjugadores.data.local.JugadorDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Implementación en memoria de [JugadorDao] para pruebas unitarias,
 * sin necesidad de Room ni de un dispositivo Android.
 * Simula el autogenerado de ids de Room asignando ids incrementales.
 */
class FakeJugadorDao : JugadorDao {

    private val items = MutableStateFlow<List<Jugador>>(emptyList())
    private var nextId = 1

    override suspend fun insertar(jugador: Jugador) {
        val conId = if (jugador.jugadorId == 0) jugador.copy(jugadorId = nextId++) else jugador
        items.value = items.value.filterNot { it.jugadorId == conId.jugadorId } + conId
    }

    override fun obtenerTodos(): Flow<List<Jugador>> = items

    override suspend fun editar(jugador: Jugador) {
        items.value = items.value.map { if (it.jugadorId == jugador.jugadorId) jugador else it }
    }

    override suspend fun eliminar(jugador: Jugador) {
        items.value = items.value.filterNot { it.jugadorId == jugador.jugadorId }
    }
}
