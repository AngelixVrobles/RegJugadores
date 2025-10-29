package com.example.regjugadores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.regjugadores.data.local.JugadorDatabase
import com.example.regjugadores.data.remote.JugadorApi
import com.example.regjugadores.data.repository.JugadorRepository
import com.example.regjugadores.data.repository.LogroRepository
import com.example.regjugadores.data.repository.PartidaRepository
import com.example.regjugadores.navigation.AppNavHost
import com.example.regjugadores.ui.*
import com.example.regjugadores.ui.theme.RegJugadoresTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RegJugadoresTheme {
                val navController = rememberNavController()

                // ✅ Inicializa Room
                val db = Room.databaseBuilder(
                    applicationContext,
                    JugadorDatabase::class.java,
                    "jugador_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                // ✅ Inicializa Retrofit
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gestionhuacalesapi.azurewebsites.net/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val jugadorApi = retrofit.create(JugadorApi::class.java)

                // ✅ Repositorios
                val jugadorRepo = JugadorRepository(db.jugadorDao(), jugadorApi)
                val partidaRepo = PartidaRepository(db.partidaDao())
                val logroRepo = LogroRepository(db.logroDao())

                // ✅ ViewModels
                val jugadorViewModel: JugadorViewModel =
                    viewModel(factory = JugadorViewModelFactory(jugadorRepo))
                val partidaViewModel: PartidaViewModel =
                    viewModel(factory = PartidaViewModelFactory(partidaRepo))
                val logroViewModel: LogroViewModel =
                    viewModel(factory = LogroViewModelFactory(logroRepo))

                // ✅ Navegación
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
