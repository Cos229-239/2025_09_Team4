package com.kodeco.memeverse.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kodeco.memeverse.composables.TopAppBar
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@Composable
fun TrendingScreen(navController: NavHostController) {
    MemeVerseTheme {
        Scaffold(
            topBar = { TopAppBar() },
        ) { innerPadding ->
            Text(text = "Trending Screen", modifier = Modifier.padding(innerPadding))
        }
    }
}

@Preview
@Composable
fun TrendingScreenPreview() {
    TrendingScreen(navController = rememberNavController())
}