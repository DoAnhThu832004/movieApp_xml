package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.Person
import kotlinx.coroutines.launch

class PersonViewModel(
    private val apiService: ApiService
) : ViewModel() {
    private val _person = MutableLiveData<List<Person>>()
    val person: LiveData<List<Person>> get() = _person
    fun getPerson(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPerson(apiKey)
                _person.postValue(response.results)
            } catch (e: Exception) {
                // Xử lý lỗi
            }
        }
    }
}