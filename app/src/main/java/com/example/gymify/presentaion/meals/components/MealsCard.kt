package com.example.gymify.presentaion.meals.components



import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext


data class Meal(
    val name: String,
    val imageRes: Int,
    val calories: Int,
    val category: String // e.g., "High Protein", "Low Carb"
)



@Composable
fun MealCard(meal: Meal) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Toast.makeText(context, "${meal.name} clicked", Toast.LENGTH_SHORT).show()
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = meal.imageRes),
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Column(Modifier.padding(8.dp)) {
                Text(meal.name, fontWeight = FontWeight.SemiBold)
                Text("${meal.calories} kcal", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}
