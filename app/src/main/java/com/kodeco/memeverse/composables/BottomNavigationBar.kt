package com.kodeco.memeverse.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kodeco.memeverse.models.Constants
import com.kodeco.memeverse.navigation.AppNavHost

@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>
) {
    // Wrap the navigation bar in AnimatedVisibility to conditionally show it
    AnimatedVisibility(
        // Observe bottom bar state
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                // Observe the backstack
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                // Observe the current route to change the icon color and label color when navigated
                val currentRoute = navBackStackEntry.value?.destination?.route
                // Map out each of the bottom nav items
                Constants.BottomNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentRoute == navItem.route,
                        // Navigate to the route on click
                        onClick = {
                            navController.navigate(navItem.route)
                        },
                        // Icon of nav item
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                        alwaysShowLabel = false,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.surface,
                            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                            selectedTextColor = MaterialTheme.colorScheme.surface,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun BottomBarAnimationApp() {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        // State of bottomBar, set to false for screens that don't need it
        when(navBackStackEntry?.destination?.route) {
            "login" -> {
                bottomBarState.value = false
            }
            "signup" -> {
                bottomBarState.value = false
            }
            else -> {
                bottomBarState.value = true
            }
        }
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                    bottomBarState = bottomBarState
                )
            },
            content = { padding ->
                // Nav host: where screens are placed
                AppNavHost(navController = navController, padding = padding)
            }
        )
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController(), rememberSaveable { mutableStateOf(true) })
}