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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.gymify.data.local.planDB.PlanExerciseEntity
import com.example.gymify.domain.models.ExcersiceItem
import com.example.gymify.presentaion.plan.PlanViewModel
import com.example.gymify.ui.theme.*
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onViewPlanClick: () -> Unit = {},
    onExerciseClick: (ExcersiceItem) -> Unit,
    planViewModel: PlanViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        planViewModel.loadPlan()
    }

    val dummyHeight = 180
    val dummyWeight = 94
    val bmi = dummyWeight / ((dummyHeight / 100f) * (dummyHeight / 100f))

    val planExercises = planViewModel.planExercises.observeAsState(emptyList())
    val groupedByDay = planExercises.value.groupBy { it.day }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            "Welcome to GYMIFY",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = SurfaceDark
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Personal Info",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryText
                )

                Spacer(Modifier.height(12.dp))

                MetricRow("Height", "$dummyHeight cm")
                MetricRow("Weight", "$dummyWeight kg")
                MetricRow(
                    "BMI",
                    "%.1f".format(bmi),
                    valueColor = PrimaryRed,
                    isHighlighted = true
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Plan Section
        Column {
            Text(
                "Your Plan",
                style = MaterialTheme.typography.titleLarge,
                color = PrimaryText,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            groupedByDay.forEach { (day, exercises) ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = day,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .background(PrimaryRed, shape = RoundedCornerShape(8.dp))
                                .padding(horizontal =10.dp, vertical = 4.dp)
                        )


                        // Horizontal Exercise List
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp) // or whatever fits your design
                        ) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier
                                    .matchParentSize()
                                    .padding(end = 16.dp)
                            ) {
                                items(exercises) { exercise ->
                                    val exerciseItem = ExcersiceItem(
                                        id = exercise.exerciseId,
                                        gifUrl = exercise.gifUrl,
                                        name = exercise.name,
                                        target = exercise.target,
                                        instructions = exercise.instructions,
                                        equipment = exercise.equipment,
                                        bodyPart = exercise.bodyPart,
                                        secondaryMuscles = exercise.secondaryMuscles,
                                    )
                                    ExerciseCard(exercise, onClick = { onExerciseClick(exerciseItem) })
                                }
                            }}

                        // Right gradient fade
                        Box(
                            modifier = Modifier
                                .align(Alignment.End)
                                .width(30.dp)
                                .fillMaxHeight()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        listOf(Color.Transparent, BackgroundDark)
                                    )
                                )
                        )
                    }

                }
            }

            Button(
                onClick = onViewPlanClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRed,
                    contentColor = PrimaryText
                )
            ) {
                Text("Edit Plan")
            }
        }
    }
}

@Composable
private fun MetricRow(
    label: String,
    value: String,
    valueColor:  Color=SecondaryText,
    isHighlighted: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = SecondaryText.copy(alpha = 0.8f),
            fontSize = 16.sp
        )
        Text(
            text = value,
            color = valueColor,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

@Composable
private fun ExerciseCard(exercise: PlanExerciseEntity, onClick: () -> Unit){
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfaceDark,
            contentColor = PrimaryText
        ),
        modifier = Modifier
            .width(140.dp)
            .padding(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = exercise.gifUrl,
                    contentDescription = "Exercise Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, SurfaceDark.copy(alpha = 0.6f))
                            )
                        )
                )
            }

            Text(
                text = exercise.name,
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryText,
                modifier = Modifier.padding(top = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
