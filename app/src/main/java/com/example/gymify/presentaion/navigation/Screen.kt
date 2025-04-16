package com.example.gymify.presentaion.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Exercises : Screen("exercises")
    object Meals : Screen("meals")
    object Profile : Screen("profile")
    object Plan : Screen("plan")
    object ExerciseDetail : Screen("exercise_detail/{exerciseId}") {
        fun passExerciseId(exerciseId: String): String {
            return "exercise_detail/$exerciseId"
        }
    }
}