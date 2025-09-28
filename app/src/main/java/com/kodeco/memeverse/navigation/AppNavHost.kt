package com.kodeco.memeverse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kodeco.memeverse.screens.LoginScreen
import com.kodeco.memeverse.screens.SignupScreen
import com.kodeco.memeverse.screens.FeedScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("feed") {FeedScreen(navController) }
    }
}