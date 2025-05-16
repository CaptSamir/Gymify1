package com.example.gymify.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.gymify.data.local.appDataBase.AppDatabase
import com.example.gymify.data.local.appDataBase.ExerciseDao
import com.example.gymify.data.local.planDB.PlanDB
import com.example.gymify.data.local.planDB.PlanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "exercise_db"
        ).build()

    @Provides
    fun provideExerciseDao(db: AppDatabase): ExerciseDao = db.exerciseDao()


    @Provides
    @Singleton
    fun providePlanDatabase(@ApplicationContext context: Context): PlanDB {
        return Room.databaseBuilder(
            context,
            PlanDB::class.java,
            "plan_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.filesDir.resolve("user_settings.preferences_pb") }
        )
    }

    @Provides
    fun providePlanDao(db: PlanDB): PlanDao {
        return db.planDao()
    }
}
