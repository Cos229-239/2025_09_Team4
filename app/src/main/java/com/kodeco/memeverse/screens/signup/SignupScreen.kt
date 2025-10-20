package com.kodeco.memeverse.screens.signup

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kodeco.memeverse.R

@Composable
fun SignupScreen(
    navController: NavController,
    signUpViewModel: SignupViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val passwordsMatch = password == confirmPassword
    val isSignupEnabled = name.isNotBlank() && username.isNotBlank() && email.isNotBlank() &&
            dob.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && passwordsMatch

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4FC3F7))
    ) {
        Box(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight()
                .background(Color(0xFF008491))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.memeverse),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "Sign up to share and store all your favorite memes",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(Modifier.height(32.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Full Name") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.extraLarge),
                shape = MaterialTheme.shapes.extraLarge,
                colors = textFieldColors()
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Username") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.extraLarge),
                shape = MaterialTheme.shapes.extraLarge,
                colors = textFieldColors()
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.extraLarge),
                shape = MaterialTheme.shapes.extraLarge,
                colors = textFieldColors()
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                value = dob,
                onValueChange = { dob = it },
                placeholder = { Text("Date of Birth") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.extraLarge),
                shape = MaterialTheme.shapes.extraLarge,
                colors = textFieldColors()
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(icon, contentDescription = null)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.extraLarge),
                shape = MaterialTheme.shapes.extraLarge,
                colors = textFieldColors()
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirm Password") },
                singleLine = true,
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                        Icon(icon, contentDescription = null)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.extraLarge),
                shape = MaterialTheme.shapes.extraLarge,
                isError = confirmPassword.isNotBlank() && !passwordsMatch,
                colors = textFieldColors()
            )

            if (confirmPassword.isNotBlank() && !passwordsMatch) {
                Text(
                    "Passwords do not match",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    signUpViewModel.signup(email, password, username) { success, error ->
                        if (success) {
                            Toast.makeText(context, "Welcome, $username!", Toast.LENGTH_SHORT).show()
                            navController.navigate("feed") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, error ?: "Signup failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                enabled = isSignupEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00695C),
                    disabledContainerColor = Color(0xFFB2EBF2)
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Sign Up", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val animatedColor by animateColorAsState(
                targetValue = if (isPressed) Color.White else Color(0xFFB2EBF2),
                label = "LoginLinkColor"
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account?", color = Color.White)
                Spacer(Modifier.width(4.dp))
                TextButton(
                    onClick = { navController.navigate("login") },
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Login",
                        color = Color(0xFFFF5722),
                        style = TextStyle(
                            textDecoration = if (isPressed) TextDecoration.Underline else TextDecoration.None
                        )
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight()
                .background(Color(0xFF008491))
        )
    }
}

@Composable
private fun textFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent
)