package com.angelixvasquez.regjugadores.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class PartidaConJugadores(
    @Embedded val partida: Partida,

    @Relation(
        parentColumn = "jugador1Id",
        entityColumn = "jugadorId"
    )
    val jugador1: Jugador,

    @Relation(
        parentColumn = "jugador2Id",
        entityColumn = "jugadorId"
    )
    val jugador2: Jugador,

    @Relation(
        parentColumn = "ganadorId",
        entityColumn = "jugadorId"
    )
    val ganador: Jugador?
)
