package com.angelixvasquez.regjugadores

import com.angelixvasquez.regjugadores.data.local.Jugador
import com.angelixvasquez.regjugadores.data.repository.JugadorRepository
import com.angelixvasquez.regjugadores.ui.JugadorViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class JugadorViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun nuevoViewModel(): Pair<JugadorViewModel, FakeJugadorDao> {
        val dao = FakeJugadorDao()
        val vm = JugadorViewModel(JugadorRepository(dao))
        return vm to dao
    }

    @Test
    fun registrarJugador_agregaUnJugador() = runTest {
        val (vm, _) = nuevoViewModel()
        val collector = launch(UnconfinedTestDispatcher(testScheduler)) { vm.jugadores.collect {} }

        vm.registrarJugador("Ana", 0)
        advanceUntilIdle()

        assertEquals(1, vm.jugadores.value.size)
        assertEquals("Ana", vm.jugadores.value.first().nombreJugador)
        collector.cancel()
    }

    @Test
    fun registrarJugador_noPermiteNombresDuplicados_ignorandoMayusculas() = runTest {
        val (vm, _) = nuevoViewModel()
        val collector = launch(UnconfinedTestDispatcher(testScheduler)) { vm.jugadores.collect {} }

        vm.registrarJugador("Ana", 0)
        advanceUntilIdle()
        vm.registrarJugador("ana", 0) // mismo nombre, distinta capitalización
        advanceUntilIdle()

        // Debe seguir habiendo solo un jugador (se rechazó el duplicado).
        assertEquals(1, vm.jugadores.value.size)
        collector.cancel()
    }

    @Test
    fun eliminarJugador_loQuitaDeLaLista() = runTest {
        val (vm, _) = nuevoViewModel()
        val collector = launch(UnconfinedTestDispatcher(testScheduler)) { vm.jugadores.collect {} }

        vm.registrarJugador("Ana", 0)
        advanceUntilIdle()
        val ana: Jugador = vm.jugadores.value.first()
        vm.eliminarJugador(ana)
        advanceUntilIdle()

        assertEquals(0, vm.jugadores.value.size)
        collector.cancel()
    }
}
