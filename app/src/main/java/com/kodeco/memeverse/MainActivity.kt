package com.kodeco.memeverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.kodeco.memeverse.navigation.AppNavHost
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemeVerseTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }

                }
        }
    }
