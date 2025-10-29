package com.example.regjugadores.data.remote

import com.example.regjugadores.data.remote.dto.JugadorDto
import retrofit2.Response
import retrofit2.http.*

interface JugadorApi {
    @GET("Jugadores")
    suspend fun getJugadores(): List<JugadorDto>

    @POST("Jugadores")
    suspend fun postJugador(@Body jugador: JugadorDto): Response<JugadorDto>

    @PUT("Jugadores/{id}")
    suspend fun putJugador(@Path("id") id: Int, @Body jugador: JugadorDto): Response<Unit>

    @DELETE("Jugadores/{id}")
    suspend fun deleteJugador(@Path("id") id: Int): Response<Unit>
}
