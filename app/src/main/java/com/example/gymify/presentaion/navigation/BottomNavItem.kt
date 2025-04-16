package com.example.gymify.presentaion.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Exercises : BottomNavItem("exercises", Icons.Default.Star, "Exercises")
    object Meals : BottomNavItem("meals", Icons.Default.Menu, "Meals")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")

}
