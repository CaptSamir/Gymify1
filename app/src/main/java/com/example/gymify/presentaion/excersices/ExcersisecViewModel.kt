package com.example.gymify.presentaion.excersices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymify.data.online.API
import com.example.gymify.domain.models.ExcersiceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import javax.inject.Inject

@HiltViewModel
class ExcersisecViewModel @Inject constructor(
    private val apiService: API
) : ViewModel() {

    private val _exerciseList = MutableLiveData<List<ExcersiceItem>>()
    val exerciseList: LiveData<List<ExcersiceItem>> get() = _exerciseList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _exerciseById = MutableLiveData<ExcersiceItem>()
    val exerciseById: LiveData<ExcersiceItem> get() = _exerciseById



    init {
        fetchExercises(limit = 10, offset = 0)

    }

    fun fetchExercises(limit: Int, offset: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getExercises(limit, offset)
                if (response.isSuccessful) {
                    _exerciseList.postValue(response.body()) // Update LiveData with the result
                } else {
                    _errorMessage.postValue("Error: ${response.code()}") // Handle error
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failure: ${e.message}") // Handle failure
            }
        }
    }

    fun getExerciseById(id: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getExerciseById(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _exerciseById.postValue(it) // post the actual item
                    } ?: run {
                        _errorMessage.postValue("Empty response")
                    }
                } else {
                    _errorMessage.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Failure: ${e.message}")
            }
        }
    }


}