package com.kodeco.memeverse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kodeco.memeverse.composables.TopBarNavigationDetail
import com.kodeco.memeverse.screens.AddScreen
import com.kodeco.memeverse.screens.FeedScreen
import com.kodeco.memeverse.screens.GeneratorScreen
import com.kodeco.memeverse.screens.LoginScreen
import com.kodeco.memeverse.screens.ProfileScreen
import com.kodeco.memeverse.screens.SignupScreen
import com.kodeco.memeverse.screens.TrendingScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("topBarNavigation") {
            TopBarNavigationDetail { navController.popBackStack() }
        }
        composable("feed") { FeedScreen(navController) }
        composable("add") { AddScreen(navController) }
        composable("generator") { GeneratorScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("trending") { TrendingScreen(navController) }
    }
}