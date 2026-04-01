package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.UpComing
import kotlinx.coroutines.launch

class UpcomingViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _upComing = MutableLiveData<List<UpComing>>()
    val upComing: LiveData<List<UpComing>> get() = _upComing
    fun getUpComing(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getUpcoming(apiKey)
                _upComing.postValue(response.results)
            } catch (e: Exception) {

            }
        }
    }
}