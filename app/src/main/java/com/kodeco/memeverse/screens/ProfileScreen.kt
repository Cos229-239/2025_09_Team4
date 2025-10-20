package com.kodeco.memeverse.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kodeco.memeverse.composables.MemeVerseScaffold
import com.kodeco.memeverse.models.AuthViewModel
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val userState by authViewModel.currentUser.collectAsState()
    val authStatus by authViewModel.authStatus.collectAsState()
    val context = LocalContext.current
    val isRestoring = remember { mutableStateOf(true) }

    LaunchedEffect(authStatus) {
        isRestoring.value = false
    }

    MemeVerseScaffold(
        bottomBarContent = { BottomNavBar(selected = "Profile", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                isRestoring.value -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                !authStatus -> {
                    Text("No user logged in", modifier = Modifier.align(Alignment.CenterHorizontally))
                    Button(onClick = { navController.navigate("login") }) {
                        Text("Go to Login")
                    }
                }

                userState == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
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

                    Spacer(Modifier.height(16.dp))

                    avatarBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem("Posts", user.postCount.toString())
                        StatItem("Followers", user.followerCount.toString())
                        StatItem("Following", user.followingCount.toString())
                    }

                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = user.bio ?: "Welcome to Memeverse!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable { navController.navigate("editProfile") }
                                .background(Color.LightGray, shape = RoundedCornerShape(24.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Edit Profile", color = Color.White, fontWeight = FontWeight.Medium)
                        }
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF00695C), shape = RoundedCornerShape(24.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text("Favorites", color = Color.White, fontWeight = FontWeight.Medium)
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    MemeGridPlaceholder()
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold)
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun MemeGridPlaceholder() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(12) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(Color.Gray)
            )
        }
    }
}

@Composable
fun BottomNavBar(selected: String, navController: NavController) {
    NavigationBar {
        listOf("Feed", "Generator", "Add", "Profile", "Trending").forEach { label ->
            NavigationBarItem(
                icon = { Icon(Icons.Default.Face, contentDescription = label) },
                label = { Text(label) },
                selected = label == selected,
                onClick = { navController.navigate(label.lowercase()) }
            )
        }
    }
}