package com.kodeco.memeverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.kodeco.memeverse.composables.BottomBarAnimationApp
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
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