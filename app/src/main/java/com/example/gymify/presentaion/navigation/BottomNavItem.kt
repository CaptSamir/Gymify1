package com.example.gymify.presentaion.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gymify.R


sealed class BottomNavItem(val route: String, val icon : Int, val label: String) {
    object Home : BottomNavItem("home", R.drawable.baseline_home_24, "Home")
    object Exercises : BottomNavItem("exercises", R.drawable.round_sports_gymnastics_24, "Exercises")
    object Ai : BottomNavItem("chat", R.drawable.baseline_smart_toy_24, "Ai Chat")
    object Profile : BottomNavItem("profile", R.drawable.round_person_outline_24, "Profile")

}

