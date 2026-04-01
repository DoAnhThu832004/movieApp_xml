package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.Popular
import kotlinx.coroutines.launch

class PopularViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _popular = MutableLiveData<List<Popular>>()
    val popular: LiveData<List<Popular>> get() = _popular
    fun getPopular(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPopular(apiKey)
                _popular.postValue(response.results)
            } catch (e: Exception) {

            }
        }
    }
}