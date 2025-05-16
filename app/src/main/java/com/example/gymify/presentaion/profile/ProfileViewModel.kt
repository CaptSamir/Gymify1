package com.example.gymify.presentaion.profile

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.gymify.data.local.DataStoreManager
import com.example.gymify.domain.notifications.NotificationWorker
import com.example.gymify.domain.repos.PlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: DataStoreManager
) : ViewModel(){

    private val _name = MutableStateFlow("Kareem")
    val name: StateFlow<String> = _name

    private val _height = MutableStateFlow("180")
    val height: StateFlow<String> = _height

    private val _weight = MutableStateFlow("94")
    val weight: StateFlow<String> = _weight


    init {
        viewModelScope.launch {
            _name.value = dataStore.userNameFlow.firstOrNull() ?: "Kareem"
            _height.value = dataStore.userHeightFlow.firstOrNull()?.toString() ?: "180"
            _weight.value = dataStore.userWeightFlow.firstOrNull()?.toString() ?: "94"
        }
    }


    fun scheduleNotification(context: Context, selectedDay: String, selectedTime: String) {
        val notificationTime = parseTime(selectedTime, selectedDay)
        val inputData = workDataOf("selectedDay" to selectedDay)
        var initialDelay = notificationTime - System.currentTimeMillis()

        if (initialDelay < 0) {
            initialDelay += TimeUnit.DAYS.toMillis(7) // Add 7 days to push it to the next week
        }

        Log.d("Notification", "Initial delay: $initialDelay")



        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            7, TimeUnit.DAYS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "NotificationWork_$selectedDay", // unique ID for each day
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )


    }



    fun parseTime(selectedTime: String, selectedDay: String): Long {
        val now = Calendar.getInstance()
        val calendar = Calendar.getInstance()

        // Set day of week
        when (selectedDay.lowercase()) {
            "monday" -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            "tuesday" -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
            "wednesday" -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
            "thursday" -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
            "friday" -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
            "saturday" -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            "sunday" -> calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        }

        // Parse time string
        val (hour, minute) = selectedTime.split(":").map { it.toIntOrNull() ?: 0 }

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // If the result is before now, shift to next week
        if (calendar.timeInMillis <= now.timeInMillis) {
            calendar.add(Calendar.DAY_OF_YEAR, 7)
        }

        return calendar.timeInMillis
    }



    fun disableNotifications(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

        val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val workManager = WorkManager.getInstance(context)

        daysOfWeek.forEach { day ->
            workManager.cancelUniqueWork("NotificationWork_$day")
        }
    }



    val darkModeFlow = dataStore.darkModeFlow
    val notificationsEnabledFlow = dataStore.notificationsEnabledFlow

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.setDarkMode(enabled)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.setNotificationsEnabled(enabled)
        }
    }



    fun setName(newName: String) {
        _name.value = newName
        viewModelScope.launch {
            dataStore.setUserName(newName)
        }
    }

    fun setHeight(newHeight: String) {
        _height.value = newHeight
        val heightInt = newHeight.toIntOrNull() ?: 0
        viewModelScope.launch {
            dataStore.setUserHeight(heightInt)
        }
    }

    fun setWeight(newWeight: String) {
        _weight.value = newWeight
        val weightInt = newWeight.toIntOrNull() ?: 0
        viewModelScope.launch {
            dataStore.setUserWeight(weightInt)
        }
    }





}