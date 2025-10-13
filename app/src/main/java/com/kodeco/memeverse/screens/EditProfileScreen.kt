package com.kodeco.memeverse.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kodeco.memeverse.models.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val userState by authViewModel.currentUser.collectAsState()
    val user = userState

    var username by remember(user) { mutableStateOf(user?.username ?: "") }
    var avatarUriState by remember { mutableStateOf<Uri?>(null) }
    var isSaving by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        avatarUriState = it
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Edit Profile") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (user == null) {
                Text("No user loaded")
                return@Column
            }

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = { imagePicker.launch("image/*") }) {
                Text("Upload Avatar")
            }

            avatarUriState?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Avatar",
                    modifier = Modifier.size(120.dp)
                )
            }

            Button(
                onClick = {
                    isSaving = true
                    authViewModel.updateProfile(username, avatarUriState?.toString())
                    isSaving = false
                    navController.popBackStack()
                },
                enabled = !isSaving
            ) {
                Text(if (isSaving) "Saving..." else "Save")
            }
        }
    }
}