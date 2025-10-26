package com.kodeco.memeverse.screens.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kodeco.memeverse.composables.TopAppBar
import com.kodeco.memeverse.models.Post
import com.kodeco.memeverse.ui.theme.MemeVerseTheme
import androidx.compose.runtime.collectAsState

@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: FeedViewModel = viewModel() // Todo: replace this with hilt dependency injection
) {
    viewModel.getPosts()
    val posts = viewModel.posts.collectAsState()
    MemeVerseTheme {
        Scaffold(
            topBar = { TopAppBar() },
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(posts.value) { post ->
                    PostItem(post)
                }
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Todo: Add author username and profile picture
            Text(
                text = post.username,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(bottom = 8.dp)
                )
            post.imageUrl?.let { imageUrl ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Post by ${post.authorId}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Text(text = post.content)
            // Todo: Add likes and comments
        }
    }
}