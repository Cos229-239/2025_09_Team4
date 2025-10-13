package com.kodeco.memeverse.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kodeco.memeverse.models.AuthViewModel
import java.io.InputStream
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val userState by authViewModel.currentUser.collectAsState()
    val authStatus by authViewModel.authStatus.collectAsState()
    val context = LocalContext.current

    // Avoid showing "No user logged in" too early — wait for auth restoration
    val isRestoring = remember { mutableStateOf(true) }

    // Once authState changes, mark restoration complete
    LaunchedEffect(authStatus) {
        isRestoring.value = false
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Profile") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            when {
                isRestoring.value -> {
                    // While waiting for session restoration
                    CircularProgressIndicator()
                }

                !authStatus -> {
                    // No authenticated user after restoration
                    Text("No user logged in")
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { navController.navigate("login") }) {
                        Text("Go to Login")
                    }
                }

                userState == null -> {
                    // Logged in, but user info still loading
                    CircularProgressIndicator()
                }

                else -> {
                    val user = userState!!
                    val avatarBitmap: ImageBitmap? = remember(user.avatarUri) {
                        user.avatarUri?.let { uriString ->
                            try {
                                val inputStream: InputStream? =
                                    context.contentResolver.openInputStream(uriString.toUri())
                                inputStream?.use { BitmapFactory.decodeStream(it)?.asImageBitmap() }
                            } catch (_: Exception) {
                                null
                            }
                        }
                    }

                    avatarBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Avatar",
                            modifier = Modifier.size(120.dp)
                        )
                    } ?: Text("No avatar uploaded")

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Username: ${user.username}")
                    Text("Email: ${user.email}")
                    Text("User ID: ${user.id}")

                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { navController.navigate("editProfile") }) {
                        Text("Edit Profile")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = {
                        authViewModel.logout()
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }) {
                        Text("Logout")
                    }
                }
            }
        }
    }
}
