package com.kodeco.memeverse.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.kodeco.memeverse.composables.MemeVerseScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratorScreen(navController: NavHostController) {
    MemeVerseScaffold(
        bottomBarContent = { BottomNavBar(selected = "Generator", navController = navController) }
    ) { innerPadding ->
        Text(text = "Generator Screen", modifier = Modifier.padding(innerPadding))
    }
}