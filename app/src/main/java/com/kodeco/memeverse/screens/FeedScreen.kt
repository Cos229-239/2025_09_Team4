package com.kodeco.memeverse.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kodeco.memeverse.composables.MemeVerseScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(navController: NavController) {
    MemeVerseScaffold(
        bottomBarContent = { BottomNavBar(selected = "Feed", navController = navController) }
    ) { innerPadding ->
            Text(text = "Feed Screen", modifier = Modifier.padding(innerPadding))
        }
    }