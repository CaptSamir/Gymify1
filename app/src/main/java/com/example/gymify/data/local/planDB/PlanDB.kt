package com.example.gymify.data.local.planDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanExerciseEntity::class], version = 1)
abstract class PlanDB : RoomDatabase() {
    abstract fun planDao(): PlanDao
}