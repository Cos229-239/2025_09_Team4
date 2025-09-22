package com.kodeco.memeverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.kodeco.memeverse.composables.BottomNavigationBar
import com.kodeco.memeverse.navigation.AppNavHost
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemeVerseTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController = navController) },
                        content = { padding ->
                            // Nav host: where screens are placed
                            AppNavHost(navController = navController, padding = padding)
                        }
                    )
                }
            }

                }
        }
    }
