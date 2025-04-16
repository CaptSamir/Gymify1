package com.example.gymify.presentaion.plan


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlanScreen(viewModel: PlanViewModel = hiltViewModel(), onBack: () -> Unit) {
    val planExercises by viewModel.planExercises.observeAsState(emptyList())
    val groupedByDay = planExercises.groupBy { it.day }
    val days = groupedByDay.entries.toList()

    LazyColumn {
        days.forEach { (day, exercises) ->
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = day,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp)
                    )

                    exercises.forEach { exercise ->onBack
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = exercise.name)
                            IconButton(onClick = { viewModel.removeFromPlan(exercise.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}