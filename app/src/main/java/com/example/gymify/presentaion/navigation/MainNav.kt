package com.example.gymify.presentaion.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.gymify.presentaion.excersices.ExerciseDetailsScreen
import com.example.gymify.presentaion.excersices.ExercisesScreen
import com.example.gymify.presentaion.home.HomeScreen
import com.example.gymify.presentaion.chat.ChatBot
import com.example.gymify.presentaion.plan.PlanScreen
import com.example.gymify.presentaion.profile.ProfileScreen


@Composable
fun MainNav() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Exercises,
        BottomNavItem.Ai,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black,
                contentColor = Color.White,
                tonalElevation = 0.dp,
            ) {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) },
                        selected = false,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen(
                onViewPlanClick = {
                    navController.navigate(Screen.Plan.route)
                }
            ) }
            composable(BottomNavItem.Exercises.route) { ExercisesScreen(
                navController = navController,
                onExerciseClick = { exercise ->
                    navController.navigate("exercise_detail/${exercise.id}")
                }
            )}
            composable(BottomNavItem.Ai.route) { ChatBot() }
            composable(BottomNavItem.Profile.route) { ProfileScreen(navController = navController) }

            composable(
                route = "exercise_detail/{exerciseId}",
                arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: ""
                ExerciseDetailsScreen(exerciseID = exerciseId)
            }



            composable(Screen.Plan.route) { PlanScreen(
                onBack = ({
                    navController.popBackStack()
                })
            ) }

        }
    }
}