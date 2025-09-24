package com.example.regjugadores.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "logros",
    foreignKeys = [
        ForeignKey(
            entity = Jugador::class,
            parentColumns = ["jugadorId"],
            childColumns = ["jugadorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["jugadorId"])] // 👈 Esto elimina el warning
)
data class Logro(
    @PrimaryKey(autoGenerate = true) val logroId: Int = 0,
    val jugadorId: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String
)
