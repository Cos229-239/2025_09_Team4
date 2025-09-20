package com.kodeco.memeverse.models

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    // Text below icon
    val label: String,
    // Icon
    val icon: ImageVector,
    // Route to the screen
    val route: String,
)
