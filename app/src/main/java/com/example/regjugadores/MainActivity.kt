package com.example.regjugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.regjugadores.data.local.JugadorDatabase
import com.example.regjugadores.data.repository.JugadorRepository
import com.example.regjugadores.navigation.AppNavHost
import com.example.regjugadores.ui.JugadorViewModel
import com.example.regjugadores.ui.theme.RegJugadoresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegJugadoresTheme {
                val jugadorViewModel: JugadorViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            val dao = JugadorDatabase.getDatabase(application).jugadorDao()
                            val repository = JugadorRepository(dao)
                            return JugadorViewModel(repository) as T
                        }
                    }
                )

                AppNavHost(viewModel = jugadorViewModel)
            }
        }
    }
}
