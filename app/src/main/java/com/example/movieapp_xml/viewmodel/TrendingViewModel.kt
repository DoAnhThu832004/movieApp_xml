package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.Trending
import kotlinx.coroutines.launch

class TrendingViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _trendingResponse = MutableLiveData<List<Trending>>()
    val trendingResponse: LiveData<List<Trending>> get() = _trendingResponse
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage
    fun getTrending(apiKey: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = apiService.getTrendingMovies(apiKey)
                // Cập nhật giá trị vào LiveData
                _trendingResponse.postValue(response.results)
            } catch (e: Exception) {
                _errorMessage.postValue("Lỗi kết nối: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}