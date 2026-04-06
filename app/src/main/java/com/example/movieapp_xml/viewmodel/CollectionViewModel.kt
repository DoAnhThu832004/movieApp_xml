package com.example.movieapp_xml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp_xml.model.ApiService
import com.example.movieapp_xml.model.Collection
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _collection = MutableLiveData<Collection>()
    val collection: LiveData<Collection> get() = _collection
    fun getCollection(collectionId: Int, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getCollection(collectionId, apiKey)
                _collection.postValue(response)
            } catch (e: Exception) {
                // Xử lý lỗi
            }
        }
    }
}