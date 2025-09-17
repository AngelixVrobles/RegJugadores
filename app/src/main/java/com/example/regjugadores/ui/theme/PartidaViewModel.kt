package com.example.regjugadores.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regjugadores.data.local.Partida
import com.example.regjugadores.data.local.PartidaConJugadores
import com.example.regjugadores.data.repository.PartidaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PartidaViewModel(private val repository: PartidaRepository) : ViewModel() {

    private val _partidasConJugadores =
        MutableStateFlow<List<PartidaConJugadores>>(emptyList())
    val partidasConJugadores: StateFlow<List<PartidaConJugadores>> = _partidasConJugadores

    init {
        cargarPartidas()
    }

    // 📌 Cargar historial de partidas
    fun cargarPartidas() {
        viewModelScope.launch {
            _partidasConJugadores.value = repository.obtenerConJugadores()
        }
    }

    // 📌 Registrar una partida nueva
    fun registrarPartida(
        jugador1Id: Int,
        jugador2Id: Int,
        ganadorId: Int?
    ) {
        viewModelScope.launch {
            val partida = Partida(
                fecha = System.currentTimeMillis(),
                jugador1Id = jugador1Id,
                jugador2Id = jugador2Id,
                ganadorId = ganadorId,
                esFinalizada = true
            )
            repository.insertar(partida)
            cargarPartidas() // ✅ Refrescar historial
        }
    }

    // 📌 Eliminar partida
    fun eliminarPartida(partida: Partida) {
        viewModelScope.launch {
            repository.eliminar(partida)
            cargarPartidas()
        }
    }
}
