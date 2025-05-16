package com.example.gymify.data.local.planDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gymify.data.local.appDataBase.Converters

@Database(entities = [PlanExerciseEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PlanDB : RoomDatabase() {
    abstract fun planDao(): PlanDao
}