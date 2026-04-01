package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.NowPlaying
import kotlinx.coroutines.launch

class NowPlayingViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _nowPlaying = MutableLiveData<List<NowPlaying>>()
    val nowPlaying: LiveData<List<NowPlaying>> get() = _nowPlaying

    fun getNowPlaying(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getNowPlaying(apiKey)
                _nowPlaying.postValue(response.results)
            } catch (e: Exception) {

            }
        }
    }

}