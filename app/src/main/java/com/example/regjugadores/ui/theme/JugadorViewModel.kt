package com.example.regjugadores.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.regjugadores.data.local.Jugador
import com.example.regjugadores.data.repository.JugadorRepository
import kotlinx.coroutines.launch

class JugadorViewModel(private val repository: JugadorRepository) : ViewModel() {

    private val _jugadores = MutableLiveData<List<Jugador>>()
    val jugadores: LiveData<List<Jugador>> = _jugadores

    private var jugadorEnEdicion: Jugador? = null

    init {
        cargarJugadores()
    }

    fun registrarJugador(nombre: String, partidas: Int) {
        viewModelScope.launch {
            val listaActual = repository.obtenerTodos()

            // 👉 Bloquear duplicados (si no estamos editando)
            if (jugadorEnEdicion == null && listaActual.any { it.nombres.equals(nombre, ignoreCase = true) }) {
                return@launch // 🚫 No insertamos duplicados
            }

            if (jugadorEnEdicion == null) {
                // 👉 Nuevo jugador
                repository.insertar(Jugador(nombres = nombre, partidas = partidas))
            } else {
                // 👉 Editar jugador existente
                val actualizado = jugadorEnEdicion!!.copy(nombres = nombre, partidas = partidas)
                repository.insertar(actualizado)
                jugadorEnEdicion = null
            }

            _jugadores.value = repository.obtenerTodos()
        }
    }

    fun setJugadorEditando(jugador: Jugador) {
        jugadorEnEdicion = jugador
    }

    fun eliminarJugador(jugador: Jugador) {
        viewModelScope.launch {
            repository.eliminar(jugador)
            _jugadores.value = repository.obtenerTodos()
        }
    }

    fun cargarJugadores() {
        viewModelScope.launch {
            _jugadores.value = repository.obtenerTodos()
        }
    }

    fun estaEditando(): Boolean = jugadorEnEdicion != null
}
