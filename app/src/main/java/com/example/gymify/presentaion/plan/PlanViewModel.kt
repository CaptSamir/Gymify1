package com.example.gymify.presentaion.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymify.data.local.planDB.PlanExerciseEntity
import com.example.gymify.domain.repos.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val planRepository: PlanRepository
) : ViewModel() {

    private val _planExercises = MutableLiveData<List<PlanExerciseEntity>>()
    val planExercises: LiveData<List<PlanExerciseEntity>> = _planExercises



    init {
        loadPlan()
    }

    fun loadPlan() {
        viewModelScope.launch {
            _planExercises.value = planRepository.getAll()
        }
    }

    fun addToPlan(exercise: PlanExerciseEntity) {
        viewModelScope.launch {
            planRepository.add(exercise)
            loadPlan() // Ensure plan is reloaded after modification
        }
    }

    fun removeFromPlan(id: String) {
        viewModelScope.launch {
            planRepository.remove(id)
            loadPlan() // Ensure plan is reloaded after modification
        }
    }

    fun clearPlan() {
        viewModelScope.launch {
            planRepository.clear()
            loadPlan()
        }
    }
}
