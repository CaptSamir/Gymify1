package com.example.gymify.presentaion.excersices

import android.util.Log
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymify.domain.models.ExcersiceItem


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

    val filteredExercises = exercisesItems?.filter {
        (selectedFilter == "All" || it.equipment ==  "With Equipment") &&
                it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Exercises", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search exercises") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Filter Row
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            listOf("All", "With Equipment", "Without Equipment").forEach { label ->
                FilterChip(
                    selected = selectedFilter == label,
                    onClick = { selectedFilter = label },
                    label = { Text(label) }
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
            }else{
                items(exercisesItems) { exercise ->
                    ExerciseCard(exercise = exercise , onExerciseClick = onExerciseClick)

                }

            }
        }
    }
}
