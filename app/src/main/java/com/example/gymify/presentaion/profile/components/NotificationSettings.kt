package com.example.gymify.presentaion.profile.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MovableContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.gymify.presentaion.profile.ProfileViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NotificationSettings(
    viewModel: ProfileViewModel,
    context: Context,
    onSaveNotificationTime: (String, String) -> Unit
) {
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    var selectedDays by remember { mutableStateOf(setOf<String>()) }
    var selectedTime by remember { mutableStateOf("00:00") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Day for Notification", style = MaterialTheme.typography.bodyMedium)

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            daysOfWeek.forEach { day ->
                val isSelected = selectedDays.contains(day)

                Button(
                    onClick = {
                        selectedDays = if (isSelected) {
                            selectedDays - day  // Deselect if already selected
                        } else {
                            selectedDays + day  // Select if not selected
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = day,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time Picker for selecting notification time
        Text("Select Notification Time", style = MaterialTheme.typography.bodyMedium)
        Text(selectedTime, style = MaterialTheme.typography.bodyMedium)

        Button(onClick = {
            // Open the time picker dialog
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    selectedTime = String.format("%02d:%02d", hour, minute)
                },
                0, 0, true
            ).show()
        }) {
            Text("Pick Time")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button to schedule the notification
        Button(onClick = {
            // Save the selected day and time
            selectedDays.forEach { day ->
                onSaveNotificationTime(day, selectedTime)
            }
        }) {
            Text("Save Notification Time")
        }
    }
}
