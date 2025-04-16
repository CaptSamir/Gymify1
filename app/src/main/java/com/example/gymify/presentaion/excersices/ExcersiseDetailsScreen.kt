package com.example.gymify.presentaion.excersices


import android.R.attr.paddingTop
import android.media.Image
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import com.example.gymify.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymify.presentaion.excersices.components.ExerciseCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.gymify.domain.models.ExcersiceItem


@Composable
fun ExerciseDetailsScreen(
    exerciseID: String,
    onAddToPlanClick: () -> Unit
) {
    val viewModel: ExcersisecViewModel = hiltViewModel()
    LaunchedEffect(exerciseID) {
        viewModel.getExerciseById(exerciseID)
    }
    val exercise by viewModel.exerciseById.observeAsState()
    val error by viewModel.errorMessage.observeAsState()

    val context = LocalContext.current
    val imageView = remember { ImageView(context) }





    when {
        exercise != null -> {
            val item = exercise!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {


                val gifEnabledLoader = ImageLoader.Builder(context)
                    .components {
                        if ( SDK_INT >= 28 ) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }.build()

                AsyncImage(
                    imageLoader = gifEnabledLoader,
                    model = item.gifUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp))

                )




                Spacer(modifier = Modifier.height(16.dp))

                Text(text = item.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Target: ${item.target}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Secondary: ${item.secondaryMuscles.joinToString()}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Instructions:", style = MaterialTheme.typography.titleMedium)
                item.instructions.forEachIndexed { index, step ->
                    Text("${index + 1}. $step", style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { onAddToPlanClick() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add to Plan")
                }
            }
        }

        error != null -> {
            Text(text = error ?: "Something went wrong")
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


