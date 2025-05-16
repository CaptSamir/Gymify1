package com.example.gymify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.WorkManager
import com.example.gymify.presentaion.navigation.MainNav
import com.example.gymify.presentaion.onBoarding.OnboardingViewModel
import com.example.gymify.presentaion.plan.PlanScreen
import com.example.gymify.presentaion.profile.ProfileScreen
import com.example.gymify.ui.theme.GymifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GymifyTheme {
                    enableEdgeToEdge()

                MainNav()





            }
        }
    }
}
