package com.example.gymify.presentaion.meals


import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import com.example.gymify.R
import com.example.gymify.presentaion.meals.components.Meal
import com.example.gymify.presentaion.meals.components.MealCard
import androidx.compose.material3.FilterChip

@Composable
fun MealsScreen(
    modifier: Modifier = Modifier,
    onMealClick: (Meal) -> Unit = {}
) {
    val meals = listOf(
        Meal("Grilled Chicken Salad", R.drawable.ic_launcher_background, 320, "High Protein"),
        Meal("Avocado Toast", R.drawable.ic_launcher_background, 250, "Low Carb"),
        Meal("Oatmeal with Berries", R.drawable.ic_launcher_background, 300, "Balanced"),
        Meal("Protein Pancakes", R.drawable.ic_launcher_background, 380, "High Protein"),
        Meal("Zucchini Noodles", R.drawable.ic_launcher_background, 200, "Low Carb")
    )

    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredMeals = meals.filter {
        (selectedCategory == "All" || it.category == selectedCategory) &&
                it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Meals", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search meals") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Filter Chips
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            listOf("All", "High Protein", "Low Carb", "Balanced").forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Meal Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredMeals) { meal ->
                MealCard(meal)
            }
        }
    }
}
