package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp_xml.model.ApiService

class MovieViewModelFactory(
    private val apiService: ApiService
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}