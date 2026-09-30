package com.angelixvasquez.regjugadores.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelixvasquez.regjugadores.data.local.Logro
import com.angelixvasquez.regjugadores.data.repository.LogroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogroViewModel(private val repository: LogroRepository) : ViewModel() {

    private val _logros = MutableStateFlow<List<Logro>>(emptyList())
    val logros: StateFlow<List<Logro>> get() = _logros

    fun obtenerLogrosJugador(jugadorId: Int) {
        viewModelScope.launch {
            repository.obtenerLogrosPorJugador(jugadorId).collect { logrosList ->
                _logros.value = logrosList
            }
        }
    }

    fun registrarLogro(jugadorId: Int, titulo: String, descripcion: String, fecha: String) {
        if (jugadorId <= 0) return  // 🚨 evitar crash si no hay jugador válido

        viewModelScope.launch {
            val logro = Logro(
                jugadorId = jugadorId,
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha
            )
            repository.insertar(logro)
            obtenerLogrosJugador(jugadorId) // refrescar lista
        }
    }


    fun eliminarLogro(id: Int, jugadorId: Int) {
        viewModelScope.launch {
            repository.eliminar(id)
            obtenerLogrosJugador(jugadorId)
        }
    }
}
