package com.example.gymify.domain.repos

import com.example.gymify.data.local.planDB.PlanDao
import com.example.gymify.data.local.planDB.PlanExerciseEntity
import javax.inject.Inject

class PlanRepository @Inject constructor(private val dao: PlanDao) {
    suspend fun add(exercise: PlanExerciseEntity) = dao.addExerciseToPlan(exercise)
    suspend fun getAll(): List<PlanExerciseEntity> = dao.getAllPlanExercises()
    suspend fun remove(id: String) = dao.removeExerciseFromPlan(id)
    suspend fun clear() = dao.clearPlan()
}