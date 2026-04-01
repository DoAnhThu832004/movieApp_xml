package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp_xml.model.ApiService

class PopularViewModelFactory(
    private val apiService: ApiService
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PopularViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PopularViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}