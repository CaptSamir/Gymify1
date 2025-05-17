package com.example.gymify.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.collections.get
import kotlin.text.get
import kotlin.text.set

class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")

        val USER_NAME = stringPreferencesKey("user_name")
        val USER_HEIGHT = intPreferencesKey("user_height")
        val USER_WEIGHT = intPreferencesKey("user_weight")

        val NOTIFICATIONS_TIME = stringPreferencesKey("notifications_time")

        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { settings ->
            settings[DARK_MODE] = enabled
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { settings ->
            settings[NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { settings ->
            settings[USER_NAME] = name
        }
    }

    suspend fun setUserHeight(height: Int) {
        dataStore.edit { settings ->
            settings[USER_HEIGHT] = height
        }
    }

    suspend fun setUserWeight(weight: Int) {
        dataStore.edit { settings ->
            settings[USER_WEIGHT] = weight
        }
    }

    val onboardingCompletedLiveData: LiveData<Boolean> = dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] ?: false
        }
        .asLiveData()


    suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = isCompleted
        }
    }


    val darkModeFlow: Flow<Boolean> = dataStore.data
        .map { it[DARK_MODE] ?: false }

    val notificationsEnabledFlow: Flow<Boolean> = dataStore.data
        .map { it[NOTIFICATIONS_ENABLED] ?: true }

    val userNameFlow: Flow<String> = dataStore.data
        .map { it[USER_NAME] ?: "Kareem" }

    val userHeightFlow: Flow<Int> = dataStore.data
        .map { it[USER_HEIGHT] ?: 180 }

    val userWeightFlow: Flow<Int> = dataStore.data
        .map { it[USER_WEIGHT] ?: 94 }



    suspend fun setNotificationTime(time: String) {
        dataStore.edit { settings ->
            settings[NOTIFICATIONS_TIME] = time
        }
    }

    val notificationTimeFlow: Flow<String> = dataStore.data
        .map { it[NOTIFICATIONS_TIME] ?: "" }

}