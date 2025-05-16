package com.example.gymify.data.local.planDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan_exercises")
data class PlanExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val exerciseId: String,
    val bodyPart: String,
    val equipment: String,
    val gifUrl: String,
    val instructions: List<String>,
    val name: String,
    val secondaryMuscles: List<String>,
    val target: String,
    val day: String = "Monday"
)