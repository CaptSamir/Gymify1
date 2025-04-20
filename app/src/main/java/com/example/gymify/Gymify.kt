package com.example.gymify

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import androidx.work.WorkManager
import com.example.gymify.domain.notifications.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Gymify : Application() {
    override fun onCreate() {
        super.onCreate()
        HiltAndroidApp()
        NotificationHelper.createNotificationChannel(this)

    }

}