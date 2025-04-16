package com.example.gymify

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Gymify : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Hilt
        HiltAndroidApp()
    }
}