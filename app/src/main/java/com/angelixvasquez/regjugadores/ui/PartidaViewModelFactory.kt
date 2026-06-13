package com.angelixvasquez.regjugadores.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelixvasquez.regjugadores.data.repository.PartidaRepository

class PartidaViewModelFactory(
    private val repository: PartidaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PartidaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PartidaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
