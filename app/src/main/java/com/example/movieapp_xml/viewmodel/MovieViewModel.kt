package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.movie.Movie
import kotlinx.coroutines.launch

class MovieViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _movies = MutableLiveData<Movie>()
    val movies: LiveData<Movie> get() = _movies
    fun getMovieDetail(movieId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getMovieDetail(movieId,apiKey)
                _movies.postValue(response)
            } catch (e: Exception) {
                // Xử lý lỗi
            }
        }
    }
}