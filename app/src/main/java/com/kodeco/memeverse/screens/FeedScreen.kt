package com.kodeco.memeverse.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kodeco.memeverse.composables.TopBarNavigationDetail
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@Composable
fun FeedScreen(navController: NavController) {
    MemeVerseTheme {
        TopBarNavigationDetail {}
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Text(text = "Feed Screen", modifier = Modifier.padding(innerPadding))
        }
    }
}