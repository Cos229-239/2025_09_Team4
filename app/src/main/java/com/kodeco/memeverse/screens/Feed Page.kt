package com.kodeco.memeverse.screens

//Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

//Model
data class Post(val id: Int, val title: String, val imageUrl: String)

//Post Data
val dummyPosts = listOf(   //Filler for now to test
    Post(1, "Funny Meme 1", "https://i.imgflip.com/1bij.jpg"),
    Post(2, "Funny Meme 2", "https://i.imgflip.com/26am.jpg"),
    Post(3, "Funny Meme 3", "https://i.imgflip.com/1otk96.jpg")
)

//Composable Function
@Composable
fun FeedScreen(navController: NavController) {
    // Screen Structure
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Feed") }
            )
        }
    ) { innerPadding ->
        // Scrolling
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            // Card UI
            items(dummyPosts) { post ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    // Column layout inside the card
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Title of the meme/post
                        Text(
                            post.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp)) // Space between title and image

                        // Load image from URL using Coil
                        Image(
                            painter = rememberAsyncImagePainter(post.imageUrl),
                            contentDescription = post.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp) // Fixed height for images
                        )
                    }
                }
            }
        }
    }
}

//Connect to login to pop up after
