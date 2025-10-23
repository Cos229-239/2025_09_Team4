package com.kodeco.memeverse.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kodeco.memeverse.composables.TopAppBar
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: FeedViewModel = viewModel() // Todo: replace this with hilt dependency injection
) {
    val posts = viewModel.getPosts()
    MemeVerseTheme {
        Scaffold(
            topBar = { TopAppBar() },
        ) { innerPadding ->
            Text(text = "Feed Screen", modifier = Modifier.padding(innerPadding))
        }
    }
}