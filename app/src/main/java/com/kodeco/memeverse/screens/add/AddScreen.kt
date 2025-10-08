package com.kodeco.memeverse.screens.add

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kodeco.memeverse.composables.TopAppBar
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@Composable
fun AddScreen(
    navController: NavHostController,
    viewModel: AddViewModel = viewModel()
) {
    // Hold state for the selected image URI
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    // Listens for the user to click on the box and opens the photo picker
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri
            viewModel.imageUri = uri
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    MemeVerseTheme {
        Scaffold(
            topBar = { TopAppBar() },
        ) { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment =  Alignment.Start,
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable {
                            // Launch the photo picker so the user can select a photo from their galery
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        if (selectedImageUri == null) {
                            Text(
                                text = "Tap to select an image",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.surface
                            )
                        } else {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(selectedImageUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Selected meme",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp, 0.dp, 10.dp, 100.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 20.dp)
                            .border(1.dp, MaterialTheme.colorScheme.onSurface)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .align(Alignment.BottomStart)
                    ) {
                        Text(
                            "Everyone",
                            modifier = Modifier
                                .padding(start = 5.dp)

                        )

                        Icon(
                            imageVector = Icons.Filled.Public,
                            contentDescription = "Public Icon",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 10.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(MaterialTheme.colorScheme.primary.hashCode()),
                            disabledContainerColor = Color(0xFFB2EBF2)
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Next")
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(bottom = 65.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    MemeVerseTheme {
        AddScreen(navController = rememberNavController())
    }
}