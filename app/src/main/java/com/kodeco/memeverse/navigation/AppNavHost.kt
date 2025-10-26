package com.kodeco.memeverse.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil3.toCoilUri
import com.kodeco.memeverse.composables.TopBarNavigationDetail
import com.kodeco.memeverse.feed.FeedScreen
import com.kodeco.memeverse.screens.GeneratorScreen
import com.kodeco.memeverse.screens.ProfileScreen
import com.kodeco.memeverse.screens.TrendingScreen
import com.kodeco.memeverse.screens.add.AddScreen
import com.kodeco.memeverse.screens.addTag.AddTagScreen
import com.kodeco.memeverse.screens.login.LoginScreen
import com.kodeco.memeverse.screens.signup.SignupScreen

@Composable
fun AppNavHost(navController: NavHostController, padding: PaddingValues) {
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
        composable("addTag") {
            val mImageUri = navController.previousBackStackEntry?.savedStateHandle?.get<String>("imageUri")
            AddTagScreen(
                // Pass the local image Uri as a nav parameter
                imageUri = mImageUri?.toUri()?.toCoilUri(),
                navController = navController
            )
        }
    }
}