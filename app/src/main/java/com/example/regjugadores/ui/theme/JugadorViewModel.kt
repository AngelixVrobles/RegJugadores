package com.example.regjugadores.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regjugadores.data.local.Jugador
import com.example.regjugadores.data.repository.JugadorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JugadorViewModel(private val repository: JugadorRepository) : ViewModel() {

    val jugadores: StateFlow<List<Jugador>> = repository.obtenerTodos()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var jugadorEnEdicion: Jugador? = null

    fun registrarJugador(nombre: String, email: String) {
        viewModelScope.launch {
            val listaActual = jugadores.value
            if (jugadorEnEdicion == null && listaActual.any { it.nombreJugador.equals(nombre, ignoreCase = true) }) return@launch

            if (jugadorEnEdicion == null) {
                repository.insertar(Jugador(nombreJugador = nombre, email = email))
            } else {
                val actualizado = jugadorEnEdicion!!.copy(
                    nombreJugador = nombre,
                    email = email
                )
                repository.editar(actualizado)
                jugadorEnEdicion = null
            }
        }
    }

    fun setJugadorEditando(jugador: Jugador) {
        jugadorEnEdicion = jugador
    }

    fun eliminarJugador(jugador: Jugador) {
        viewModelScope.launch {
            repository.eliminar(jugador)
        }
    }

    fun estaEditando(): Boolean = jugadorEnEdicion != null
}
