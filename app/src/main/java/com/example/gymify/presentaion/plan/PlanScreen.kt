package com.example.gymify.presentaion.plan


import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.FilterChip




@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlanScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSavePlanClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var planName by remember { mutableStateOf("") }
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val selectedDays = remember { mutableStateListOf<String>() }

    // Dummy data
    val dummyExercises = listOf("Push Ups", "Squats", "Bench Press")
    val dummyMeals = listOf("Oatmeal", "Grilled Chicken", "Protein Shake")

    // Store selected items
    val planData = remember { mutableStateMapOf<String, Pair<MutableList<String>, MutableList<String>>>() }

    Column(modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Create Your Plan", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = planName,
            onValueChange = { planName = it },
            label = { Text("Plan Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text("Select Days:", fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))

        FlowRow{
            daysOfWeek.forEach { day ->
                val isSelected = selectedDays.contains(day)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        if (isSelected) selectedDays.remove(day) else selectedDays.add(day)
                        if (!planData.containsKey(day)) {
                            planData[day] = Pair(mutableListOf(), mutableListOf())
                        }
                    },
                    label = { Text(day) }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        selectedDays.forEach { day ->
            Text(day, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    planData[day]?.first?.add(dummyExercises.random())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Exercise")
            }

            planData[day]?.first?.forEach {
                Text("• $it", modifier = Modifier.padding(start = 12.dp, top = 4.dp))
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    planData[day]?.second?.add(dummyMeals.random())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Meal")
            }

            planData[day]?.second?.forEach {
                Text("🍽 $it", modifier = Modifier.padding(start = 12.dp, top = 4.dp))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                Toast.makeText(context, "Plan Saved!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Plan")
        }
    }
}
