package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.PersonDetail
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val apiService: ApiService
) : ViewModel() {
    private val _personDetail = MutableLiveData<PersonDetail>()
    val personDetail: LiveData<PersonDetail> get() = _personDetail
    fun getPersonDetail(apiKey: String,personId: Int,) {
        viewModelScope.launch {
            try {
                val response = apiService.getPersonDetail(personId, apiKey)
                _personDetail.postValue(response)
            } catch (e: Exception) {
                // Xử lý lỗi
            }
        }
    }
}