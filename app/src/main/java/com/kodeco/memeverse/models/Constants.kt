package com.kodeco.memeverse.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TextFields

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
                label = "Feed",
                icon = Icons.AutoMirrored.Filled.Feed,
                route = "feed"
        ),        BottomNavItem(
                label = "Generator",
                icon = Icons.Filled.TextFields,
                route = "generator"
        ),        BottomNavItem(
                label = "Add",
                icon = Icons.Filled.AddCircleOutline,
                route = "add"
        ),BottomNavItem(
                label = "Profile",
                icon = Icons.Filled.Person,
                route = "profile"
        ),BottomNavItem(
                label = "Trending",
                icon = Icons.AutoMirrored.Filled.TrendingUp,
                route = "trending"
        ),
    )
}