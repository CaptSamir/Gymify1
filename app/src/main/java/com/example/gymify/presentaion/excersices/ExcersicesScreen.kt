package com.example.gymify.presentaion.excersices

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import com.example.gymify.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymify.presentaion.excersices.components.ExerciseCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymify.domain.models.ExcersiceItem
import com.example.gymify.ui.theme.*

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    viewModel: ExcersisecViewModel = hiltViewModel(),
    navController: NavController,
    onExerciseClick: (ExcersiceItem) -> Unit = {},
) {

    val exercisesItems by viewModel.exerciseList.observeAsState(emptyList())
    val error by viewModel.errorMessage.observeAsState()

    var selectedFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredExercises = exercisesItems.filter { exercise ->
        val matchesFilter = selectedFilter == "All" || exercise.target.equals(selectedFilter, ignoreCase = true)
        val matchesSearch = exercise.name.contains(searchQuery, ignoreCase = true)
        matchesFilter && matchesSearch
    }

    Column(
        modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        // Header
        Text(
            "Exercises",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkTextPrimary // Set text color
        )

        Spacer(Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search exercises", color = DarkTextSecondary) }, // Label color
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkPrimary,
                unfocusedBorderColor = DarkSecondaryContainer,
                focusedLabelColor = DarkTextPrimary,
                unfocusedLabelColor = DarkTextSecondary
            )
        )

        Spacer(Modifier.height(12.dp))

        // Filter Chips
        val muscleGroups = listOf(
            "All", "abductors", "abs", "adductors", "biceps", "calves",
            "cardiovascular system", "delts", "forearms", "glutes", "hamstrings",
            "lats", "levator scapulae", "pectorals", "quads", "serratus anterior",
            "spine", "traps", "triceps", "upper back"
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            muscleGroups.forEach { label ->
                FilterChip(
                    selected = selectedFilter == label,
                    onClick = { selectedFilter = label },
                    label = { Text(label) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = if (selectedFilter == label) DarkPrimary else DarkSecondaryContainer,
                        labelColor = if (selectedFilter == label) DarkTextPrimary else DarkTextSecondary
                    )
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Exercises Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (error != null) {
                Log.d("Error", error.toString())
            } else {
                items(filteredExercises) { exercise ->
                    ExerciseCard(exercise = exercise, onExerciseClick = onExerciseClick)
                }
            }
        }
    }
}

