package com.example.gymify.presentaion.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.gymify.presentaion.excersices.ExercisesScreen
import com.example.gymify.presentaion.home.HomeScreen
import com.example.gymify.presentaion.meals.MealsScreen
import com.example.gymify.presentaion.plan.PlanScreen
import com.example.gymify.presentaion.profile.ProfileScreen

//@Composable
//fun NavigationSetup(navController: NavHostController) {
//    val navBackStackEntry = navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry.value?.destination?.route
//
//    // Set up your NavHost to manage both bottom navigation and individual screens
//    NavHost(navController = navController, startDestination = Screen.Home.route) {
//        composable(Screen.Home.route) {
//            HomeScreen()
//        }
//        composable(Screen.Exercises.route) {
//            ExercisesScreen()
//        }
//        composable(Screen.Meals.route) {
//            MealsScreen()
//        }
//        composable(Screen.Profile.route) {
//           // ProfileScreen()
//        }
//        composable(Screen.Plan.route) {
//            PlanScreen()
//        }
//    }

    // BottomNavigation UI
 //   BottomNavBar(navController = navController, currentRoute = currentRoute)
//}
