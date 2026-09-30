package com.angelixvasquez.regjugadores.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RegistroLogroScreen(
    viewModel: LogroViewModel,
    jugadorId: Int,
    onGuardar: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Fecha actual en formato legible
    val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registrar Logro", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (jugadorId <= 0) {
                    // 🚨 No deberíamos permitir guardar un logro sin jugador válido
                    Log.e("RegistroLogroScreen", "JugadorId inválido: $jugadorId")
                    return@Button
                }

                if (titulo.isBlank() || descripcion.isBlank()) {
                    Log.e("RegistroLogroScreen", "Campos vacíos: titulo='$titulo', descripcion='$descripcion'")
                    return@Button
                }

                // ✅ Si todo está correcto, guardamos el logro
                viewModel.registrarLogro(
                    jugadorId = jugadorId,
                    titulo = titulo.trim(),
                    descripcion = descripcion.trim(),
                    fecha = fecha
                )

                Log.i("RegistroLogroScreen", "Logro guardado correctamente para jugadorId=$jugadorId")

                onGuardar()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Logro")
        }
    }
}
