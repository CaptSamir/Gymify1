package com.example.gymify.domain.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val selectedDay = inputData.getString("selectedDay") ?: "Pass ONE" // Default to Monday if no value is passed
        sendNotification(applicationContext, selectedDay)

        return Result.success()
    }

    fun sendNotification(context: Context, selectedDay: String) {
        val title = "Workout Reminder for $selectedDay"
        val content = "It's time for your workout on $selectedDay!"

        NotificationHelper.sendNotification(context, title, content)
    }


}