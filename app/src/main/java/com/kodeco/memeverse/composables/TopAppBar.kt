package com.kodeco.memeverse.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.memeverse.R
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    MemeVerseTheme {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image (
                        painter = painterResource(id = R.drawable.memeverse),
                        contentDescription = "MemeVerse Logo",
                        modifier = Modifier,
                        contentScale = ContentScale.Crop
                    )
                },
                actions = {
                    IconButton(onClick = {/* TODO: handle notification bell click */ }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth())
            {
                // Screen content will go here.
            }
        }
    )
    }
}

@Preview
@Composable
fun PreviewTopAppBar() {
    TopAppBar()
}