package com.angelixvasquez.regjugadores.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaLogrosScreen(
    viewModel: LogroViewModel,
    jugadorId: Int,
    onBack: () -> Unit,
    onAgregarClick: () -> Unit
) {
    val logros = viewModel.logros.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Logros del Jugador") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Logro")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (logros.value.isEmpty()) {
                Text("No hay logros registrados.")
            } else {
                logros.value.forEach { logro ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "🏆 ${logro.titulo}", style = MaterialTheme.typography.titleMedium)
                            Text(text = logro.descripcion, style = MaterialTheme.typography.bodyMedium)
                            Text(text = "📅 ${logro.fecha}", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}
