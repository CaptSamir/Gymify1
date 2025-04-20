package com.example.gymify.presentaion.profile


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gymify.data.local.DataStoreManager
import com.example.gymify.presentaion.navigation.Screen
import com.example.gymify.presentaion.profile.components.NotificationSettings
import com.example.gymify.presentaion.profile.components.ProfileItem
import com.example.gymify.presentaion.profile.components.ProfileToggleItem

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onEditInfoClick: () -> Unit = {},
    onLogOutClick: () -> Unit = {}
) {

    val viewModel : ProfileViewModel =  hiltViewModel()
    val context = LocalContext.current

    val notificationsEnabled by viewModel.notificationsEnabledFlow.collectAsState(initial = false)
    val darkModeEnabled by viewModel.darkModeFlow.collectAsState(initial = false)


    var showNotificationSettings by remember { mutableStateOf(false) }

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Text("Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        // User Info Card
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Kareem", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Height: 180 cm")
                Text("Weight: 94 kg")
                Text("BMI: 29.0", color = Color.Gray)

                Spacer(Modifier.height(12.dp))

                Button(onClick = {
                    Toast.makeText(context, "Edit Info Clicked", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Edit Info")
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Settings
        Text("Settings", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(12.dp))

        ProfileToggleItem(
            label = "Notifications",
            checked = notificationsEnabled,
            onCheckedChange = {
                viewModel.setNotificationsEnabled(it)
                if (it) {
                    Toast.makeText(context, "Notifications Enabled", Toast.LENGTH_SHORT).show()
                    showNotificationSettings = true
                } else {
                    showNotificationSettings = false
                    viewModel.disableNotifications(context)
                    Toast.makeText(context, "Notifications Disabled", Toast.LENGTH_SHORT).show()
                }
            }
        )

        if (showNotificationSettings) {
            NotificationSettings(
                viewModel = viewModel,
                onSaveNotificationTime = { selectedDay, selectedTime ->
                    viewModel.scheduleNotification(context, selectedDay, selectedTime)
                    showNotificationSettings = false
                },
                context = context
            )
        }

        ProfileToggleItem(
            label = "Dark Mode",
            checked = darkModeEnabled,
            onCheckedChange = {
                viewModel.setDarkMode(it)
            }
        )


        ProfileItem("Log Out") {
            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
        }
    }
}
