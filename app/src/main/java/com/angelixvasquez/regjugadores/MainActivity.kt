package com.angelixvasquez.regjugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.angelixvasquez.regjugadores.data.local.JugadorDatabase
import com.angelixvasquez.regjugadores.data.repository.JugadorRepository
import com.angelixvasquez.regjugadores.data.repository.PartidaRepository
import com.angelixvasquez.regjugadores.data.repository.LogroRepository
import com.angelixvasquez.regjugadores.navigation.AppNavHost
import com.angelixvasquez.regjugadores.ui.*
import com.angelixvasquez.regjugadores.ui.theme.RegJugadoresTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RegJugadoresTheme {
                val navController = rememberNavController()

                // ✅ Inicializamos la base de datos y repositorios
                val db = JugadorDatabase.getDatabase(applicationContext)
                val jugadorRepo = JugadorRepository(db.jugadorDao())
                val partidaRepo = PartidaRepository(db.partidaDao())
                val logroRepo = LogroRepository(db.logroDao())

                // ✅ ViewModels con Factory
                val jugadorViewModel: JugadorViewModel =
                    viewModel(factory = JugadorViewModelFactory(jugadorRepo))
                val partidaViewModel: PartidaViewModel =
                    viewModel(factory = PartidaViewModelFactory(partidaRepo))
                val logroViewModel: LogroViewModel =
                    viewModel(factory = LogroViewModelFactory(logroRepo))

                // ✅ Pasamos todo al NavHost
                AppNavHost(
                    navController = navController,
                    jugadorViewModel = jugadorViewModel,
                    partidaViewModel = partidaViewModel,
                    logroViewModel = logroViewModel
                )
            }
        }
    }
}
