package com.kodeco.memeverse.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kodeco.memeverse.composables.MemeVerseScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(navController: NavHostController) {
    MemeVerseScaffold(
        bottomBarContent = { BottomNavBar(selected = "Trending", navController = navController) }
    ) { innerPadding ->
        Text(text = "Trending Screen", modifier = Modifier.padding(innerPadding))
    }
}

@Preview
@Composable
fun TrendingScreenPreview() {
    TrendingScreen(navController = rememberNavController())
}