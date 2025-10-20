package com.kodeco.memeverse.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.kodeco.memeverse.composables.MemeVerseScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController) {
    var imageUri: Uri?
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                imageUri = uri
                Log.d("PhotoPicker", "Image URI Stored at: $imageUri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    SideEffect {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
    MemeVerseScaffold(
        bottomBarContent = { BottomNavBar(selected = "Add", navController = navController) }
    ) { innerPadding ->
            Text(text = "Add Screen", modifier = Modifier.padding(innerPadding))
        }
    }