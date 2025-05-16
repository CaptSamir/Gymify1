package com.example.gymify.data.local.planDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExerciseToPlan(exercise: PlanExerciseEntity)

    @Query("SELECT * FROM plan_exercises")
    suspend fun getAllPlanExercises(): List<PlanExerciseEntity>

    @Query("DELETE FROM plan_exercises WHERE id = :id")
    suspend fun removeExerciseFromPlan(id: Int)

    @Query("DELETE FROM plan_exercises")
    suspend fun clearPlan()
}