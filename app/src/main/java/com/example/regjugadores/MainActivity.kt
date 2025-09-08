package com.example.regjugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.regjugadores.data.local.JugadorDatabase
import com.example.regjugadores.data.repository.JugadorRepository
import com.example.regjugadores.ui.JugadorViewModel
import com.example.regjugadores.ui.RegistroJugadorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ⚡ Creamos la base de datos Room
        val db = Room.databaseBuilder(
            applicationContext,
            JugadorDatabase::class.java,
            "jugadores-db"
        ).build()

        // ⚡ Repositorio (para acceder a la DB)
        val repository = JugadorRepository(db.jugadorDao())

        // ⚡ ViewModel (maneja la lógica)
        val viewModel = JugadorViewModel(repository)

        // ⚡ Pantalla principal
        setContent {
            RegistroJugadorScreen(viewModel)
        }
    }
}
