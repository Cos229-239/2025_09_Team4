package com.kodeco.memeverse.screens.login

import androidx.compose.foundation.Image
import android.widget.Toast
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kodeco.memeverse.R

@Composable
fun LoginScreen(
    navController: NavController
) {
    val loginViewModel: LoginViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isLoginEnabled = email.isNotBlank() && password.isNotBlank()

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

            Spacer(Modifier.height(32.dp))

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    showError = false
                },
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
                value = password,
                onValueChange = {
                    password = it
                    showError = false
                },
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
                isError = showError,
                colors = textFieldColors()
            )

            if (showError) {
                Text(
                    "Email or password is incorrect",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    loginViewModel.signIn(email.trim(), password.trim()) { success, error ->
                        if (success) {
                            Toast.makeText(context, "Signed in successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate("feed") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        } else {
                            showError = true
                            Toast.makeText(context, error ?: "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                enabled = isLoginEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00695C),
                    disabledContainerColor = Color(0xFFB2EBF2)
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Log In", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = { /* Handle forgot password */ }) {
                Text("Forgot Password?", color = Color.White)
            }

            Spacer(Modifier.height(8.dp))

            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Not a member yet?", color = Color.White)
                Spacer(Modifier.width(4.dp))
                TextButton(
                    onClick = { navController.navigate("signup") },
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Sign Up",
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