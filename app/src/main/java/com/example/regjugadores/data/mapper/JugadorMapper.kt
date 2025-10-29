package com.example.regjugadores.data.mapper

import com.example.regjugadores.data.local.Jugador
import com.example.regjugadores.data.remote.dto.JugadorDto

fun JugadorDto.toEntity(): Jugador =
    Jugador(jugadorId = jugadorId, nombreJugador = nombres, email = email)

fun Jugador.toDto(): JugadorDto =
    JugadorDto(jugadorId = jugadorId, nombres = nombreJugador, email = email)
