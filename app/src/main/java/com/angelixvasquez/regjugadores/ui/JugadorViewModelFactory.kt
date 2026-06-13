package com.angelixvasquez.regjugadores.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelixvasquez.regjugadores.data.repository.JugadorRepository

class JugadorViewModelFactory(
    private val repository: JugadorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JugadorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JugadorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
