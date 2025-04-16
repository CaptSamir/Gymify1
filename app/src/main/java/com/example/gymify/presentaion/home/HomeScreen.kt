package com.example.gymify.presentaion.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCreatePlanClick: () -> Unit = {},
    onViewPlanClick: () -> Unit = {}
) {
    val dummyHeight = 180 // cm
    val dummyWeight = 94 // kg
    val bmi = dummyWeight / ((dummyHeight / 100f) * (dummyHeight / 100f))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Welcome to GYMIFY 💪", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        // Personal Info + BMI
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

        // Plan Section
        Text("Your Current Plan", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val hasPlan = true // Dummy state for now

                if (hasPlan) {
                    Text("Plan: Full Body 4 Days 🔥", fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onViewPlanClick) {
                        Text("View Plan")
                    }
                } else {
                    Text("No plan created yet 🧐")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = onCreatePlanClick) {
                        Text("Create a Plan")
                    }
                }
            }
        }
    }
}
