package com.kodeco.memeverse.screens.addTag

import android.net.Uri
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PrivateConnectivity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.kodeco.memeverse.composables.TopAppBar
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@Composable
fun AddTagScreen(
    navController: NavHostController,
    mSelectedImageUri: Uri
) {
    var caption by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    // Set the state for the dropdown menu
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val options = listOf("Public", "Private")
    // set a state variable for the option position
    val itemPosition = remember { mutableStateOf(0) }

    MemeVerseTheme {
        Scaffold(
            topBar = { TopAppBar() },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.Start,
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        // Display the users selection of an image
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(mSelectedImageUri)
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp, 0.dp, 10.dp, 100.dp)
                ) {
                    TextField(
                        value = caption,
                        onValueChange = { caption = it },
                        placeholder = { Text("Caption") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.extraLarge)
                            .background(MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.extraLarge),
                    )
                    TextField(
                        value = tags,
                        onValueChange = { tags = it },
                        placeholder = { Text("Tags") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .background(MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.extraLarge),
                    )
                    Row(
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 20.dp)
                            .border(1.dp, MaterialTheme.colorScheme.onSurface)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .align(Alignment.BottomStart)
                            // Enable the dropdown menu when the row is clicked
                            .clickable {
                                isDropdownExpanded.value = true
                            }
                    ) {
                        Text(
                            // Dynamically display the selected option, or public by default.
                            text = (options[itemPosition.value]),
                            modifier = Modifier
                                .padding(start = 5.dp)
                        )
                        Icon(
                            // Dynamically display the correct icon based on the selected option
                            imageVector = if(options[itemPosition.value] == "Private") Icons.Filled.PrivateConnectivity else Icons.Filled.Public,
                            contentDescription = "Dropdown Icon",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    DropdownMenu(
                        expanded = isDropdownExpanded.value,
                        onDismissRequest = { isDropdownExpanded.value = false }
                    ) {
                        options.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = option)
                                },
                                onClick = {
                                    isDropdownExpanded.value = false
                                    itemPosition.value = index
                                }
                            )
                        }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 10.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
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
                    // Horizontal divider for above the button
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

@Preview
@Composable
fun PreviewAddTagScreen() {
    AddTagScreen(navController = NavHostController(LocalContext.current), Uri.EMPTY)
}