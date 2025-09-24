package com.kodeco.memeverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.kodeco.memeverse.composables.BottomBarAnimationApp
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemeVerseTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Function to present the bottom bar when needed
                    BottomBarAnimationApp()
                }
            }

                }
        }
    }