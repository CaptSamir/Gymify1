package com.example.gymify.presentaion.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.gymify.ui.theme.*

@Composable
fun HomeScreen() {
    // State variables
    var height by remember { mutableIntStateOf(180) }
    var weight by remember { mutableIntStateOf(94) }
    val bmi by remember {
        derivedStateOf { weight / ((height / 100f) * (height / 100f)) }
    }

    var days by remember {
        mutableStateOf(
            listOf(
                DayPlan("Monday", "Chest Day", listOf("Push-ups", "Bench Press")),
                DayPlan("Tuesday", "Leg Day", listOf("Squats", "Lunges"))
            )
        )
    }

    var showEditMetricsDialog by remember { mutableStateOf(false) }
    var showAddDayDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                // Welcome message moved inside item block
                Text("Welcome to GYMIFY 💪",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )

                Spacer(Modifier.height(24.dp))
                // BMI Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceDark)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Body Metrics",
                                color = PrimaryText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            IconButton(
                                onClick = { showEditMetricsDialog = true },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = PrimaryRed
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        MetricRow(title = "Height", value = "$height cm", color = SecondaryText)
                        MetricRow(title = "Weight", value = "$weight kg", color = SecondaryText)
                        MetricRow(
                            title = "BMI",
                            value = "%.1f".format(bmi),
                            color = PrimaryRed,
                            valueWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Plan Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Weekly Plan",
                        color = PrimaryText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    FilledIconButton(
                        onClick = { showAddDayDialog = true },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = PrimaryRed,
                            contentColor = PrimaryText
                        ),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Day")
                    }
                }
            }

            // Days List
            items(days) { day ->
                DayPlanItem(day = day, dividerColor = DividerColor, surfaceColor = SurfaceDark)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Edit Metrics Dialog
        if (showEditMetricsDialog) {
            MetricsEditDialog(
                height = height,
                weight = weight,
                onHeightChange = { height = it },
                onWeightChange = { weight = it },
                onDismiss = { showEditMetricsDialog = false },
                surfaceColor = SurfaceDark,
                primaryText = PrimaryText,
                secondaryText = SecondaryText,
                accentColor = PrimaryRed
            )
        }

        // Add Day Dialog
        if (showAddDayDialog) {
            AddDayDialog(
                onAddDay = { name, title ->
                    days = days + DayPlan(name, title, emptyList())
                    showAddDayDialog = false
                },
                onDismiss = { showAddDayDialog = false },
                surfaceColor = SurfaceDark,
                primaryText = PrimaryText,
                secondaryText = SecondaryText,
                accentColor = PrimaryRed
            )
        }
    }
}

@Composable
private fun MetricRow(
    title: String,
    value: String,
    color: Color,
    valueWeight: FontWeight = FontWeight.Normal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = SecondaryText)
        Text(value, color = color, fontWeight = valueWeight)
    }
}

@Composable
private fun DayPlanItem(
    day: DayPlan,
    dividerColor: Color,
    surfaceColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Day Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        day.day,
                        color = PrimaryText,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        day.title,
                        color = SecondaryText,
                        fontSize = 14.sp
                    )
                }
                IconButton(
                    onClick = { /* Edit day */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = PrimaryRed
                    )
                }
            }

            // Exercises List
            Column(modifier = Modifier.padding(top = 12.dp)) {
                day.exercises.forEachIndexed { index, exercise ->
                    ExerciseItem(exercise = exercise)
                    if (index < day.exercises.size - 1) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = dividerColor,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                if (day.exercises.isEmpty()) {
                    Text(
                        "No exercises - Add some!",
                        color = SecondaryText,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseItem(exercise: String) {
    Column {
        Text(
            text = "- $exercise",
            color = PrimaryText,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Exercise GIF placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(DividerColor.copy(alpha = 0.5f))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "Exercise GIF",
                    color = SecondaryText
                )
            }
        }
    }
}

// Dialog Components (would be in separate files in production)
@Composable
private fun MetricsEditDialog(
    height: Int,
    weight: Int,
    onHeightChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    onDismiss: () -> Unit,
    surfaceColor: Color,
    primaryText: Color,
    secondaryText: Color,
    accentColor: Color
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Edit Body Metrics",
                    color = primaryText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = height.toString(),
                    onValueChange = { onHeightChange(it.toIntOrNull() ?: height) },
                    label = { Text("Height (cm)", color = secondaryText) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = surfaceColor,
                        unfocusedContainerColor = surfaceColor,
                        focusedTextColor = primaryText,
                        unfocusedTextColor = primaryText,
                        focusedLabelColor = accentColor,
                        unfocusedLabelColor = secondaryText,
                        focusedIndicatorColor = accentColor,
                        unfocusedIndicatorColor = DividerColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = weight.toString(),
                    onValueChange = { onWeightChange(it.toIntOrNull() ?: weight) },
                    label = { Text("Weight (kg)", color = secondaryText) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = surfaceColor,
                        unfocusedContainerColor = surfaceColor,
                        focusedTextColor = primaryText,
                        unfocusedTextColor = primaryText,
                        focusedLabelColor = accentColor,
                        unfocusedLabelColor = secondaryText,
                        focusedIndicatorColor = accentColor,
                        unfocusedIndicatorColor = DividerColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = secondaryText
                        )
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor,
                            contentColor = primaryText
                        )
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
private fun AddDayDialog(
    onAddDay: (String, String) -> Unit,
    onDismiss: () -> Unit,
    surfaceColor: Color,
    primaryText: Color,
    secondaryText: Color,
    accentColor: Color
) {
    var dayName by remember { mutableStateOf("") }
    var dayTitle by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Add New Training Day",
                    color = primaryText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = dayName,
                    onValueChange = { dayName = it },
                    label = { Text("Day Name (e.g., Monday)", color = secondaryText) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = surfaceColor,
                        unfocusedContainerColor = surfaceColor,
                        focusedTextColor = primaryText,
                        unfocusedTextColor = primaryText,
                        focusedLabelColor = accentColor,
                        unfocusedLabelColor = secondaryText,
                        focusedIndicatorColor = accentColor,
                        unfocusedIndicatorColor = DividerColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = dayTitle,
                    onValueChange = { dayTitle = it },
                    label = { Text("Day Title (e.g., Chest Day)", color = secondaryText) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = surfaceColor,
                        unfocusedContainerColor = surfaceColor,
                        focusedTextColor = primaryText,
                        unfocusedTextColor = primaryText,
                        focusedLabelColor = accentColor,
                        unfocusedLabelColor = secondaryText,
                        focusedIndicatorColor = accentColor,
                        unfocusedIndicatorColor = DividerColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = secondaryText
                        )
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (dayName.isNotBlank() && dayTitle.isNotBlank()) {
                                onAddDay(dayName, dayTitle)
                                dayName = ""
                                dayTitle = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accentColor,
                            contentColor = primaryText
                        ),
                        enabled = dayName.isNotBlank() && dayTitle.isNotBlank()
                    ) {
                        Text("Add Day")
                    }
                }
            }
        }
    }
}

private data class DayPlan(
    val day: String,
    val title: String,
    val exercises: List<String>
)
