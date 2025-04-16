package com.example.gymify.data.local.planDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plan_exercises")
data class PlanExerciseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val gifUrl: String,
    val target: String,
    val day: String = "Monday"
)