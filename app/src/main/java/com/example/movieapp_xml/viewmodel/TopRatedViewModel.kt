package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.TopRated
import kotlinx.coroutines.launch

class TopRatedViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _topRated = MutableLiveData<List<TopRated>>()
    val topRated: LiveData<List<TopRated>> get() = _topRated
    fun getTopRated(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getTopRated(apiKey)
                _topRated.postValue(response.results)
            } catch (e: Exception) {

            }
        }
    }
}