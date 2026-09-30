package com.angelixvasquez.regjugadores.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelixvasquez.regjugadores.data.repository.LogroRepository

class LogroViewModelFactory(
    private val repository: LogroRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogroViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
