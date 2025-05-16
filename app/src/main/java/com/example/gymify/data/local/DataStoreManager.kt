package com.example.gymify.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

    companion object {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")

        val USER_NAME = stringPreferencesKey("user_name")
        val USER_HEIGHT = intPreferencesKey("user_height")
        val USER_WEIGHT = intPreferencesKey("user_weight")

        // New key for onboarding completed
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }
        // New key for onboarding completed

    // Existing setters...
    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[DARK_MODE] = enabled
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { settings ->
            settings[USER_NAME] = name
        }
    }

    suspend fun setUserHeight(height: Int) {
        context.dataStore.edit { settings ->
            settings[USER_HEIGHT] = height
        }
    }

    suspend fun setUserWeight(weight: Int) {
        context.dataStore.edit { settings ->
            settings[USER_WEIGHT] = weight
        }
    }

    // New: Save onboarding completed
    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { settings ->
            settings[ONBOARDING_COMPLETED] = completed
        }
    }

    // Existing getters...
    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { it[DARK_MODE] ?: false }

    val notificationsEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { it[NOTIFICATIONS_ENABLED] ?: true }

    val userNameFlow: Flow<String> = context.dataStore.data
        .map { it[USER_NAME] ?: "Kareem" }

    val userHeightFlow: Flow<Int> = context.dataStore.data
        .map { it[USER_HEIGHT] ?: 180 }

    val userWeightFlow: Flow<Int> = context.dataStore.data
        .map { it[USER_WEIGHT] ?: 94 }

    // New: Read onboarding completed state
    val onboardingCompletedFlow: Flow<Boolean> = context.dataStore.data
        .map { it[ONBOARDING_COMPLETED] ?: false }
}