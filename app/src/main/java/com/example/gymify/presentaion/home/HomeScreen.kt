package com.example.gymify.presentaion.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.gymify.presentaion.plan.PlanViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onViewPlanClick: () -> Unit = {},
    planViewModel: PlanViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        planViewModel.loadPlan()
    }

    val dummyHeight = 180 // cm
    val dummyWeight = 94 // kg
    val bmi = dummyWeight / ((dummyHeight / 100f) * (dummyHeight / 100f))

    val planExercises = planViewModel.planExercises.observeAsState(emptyList())
    val groupedByDay = planExercises.value.groupBy { it.day }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text("Welcome to GYMIFY 💪", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Personal Info", fontWeight = FontWeight.SemiBold)
                Text("Height: $dummyHeight cm")
                Text("Weight: $dummyWeight kg")
                Spacer(Modifier.height(8.dp))
                Text("💡 BMI: ${"%.1f".format(bmi)}", fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(24.dp))

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Your Plan", style = MaterialTheme.typography.titleMedium)

            groupedByDay.forEach { (day, exercises) ->
                Text(
                    text = day,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                // Iterate through exercises for the current day
                exercises.forEach { exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Exercise Name
                        Text(
                            text = exercise.name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )

                        // Exercise GIF
                        AsyncImage(
                            model = exercise.gifUrl,
                            contentDescription = "Exercise GIF",
                            modifier = Modifier
                                .size(50.dp) // Set size for the GIF
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Add a space between days
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Button to edit the plan
            Button(
                onClick = onViewPlanClick,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Edit Plan")
            }
        }
    }
}