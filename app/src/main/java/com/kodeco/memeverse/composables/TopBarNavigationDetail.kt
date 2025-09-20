package com.kodeco.memeverse.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNavigationDetail(
    navigateBack: () -> Unit,
) {
    MemeVerseTheme {

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        AppBarTitleWithSubtitle(
                            title = "Posts",
                            subtitle = "TheRealJoeSmith"
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,

                    ),
                    navigationIcon = {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back Arrow",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    },
                )
            }
        ) { innerPadding ->
            Text("Post Detail / Notifications Screen goes here",
            modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Preview
@Composable
fun PreviewTopBarNavigation() {
    TopBarNavigationDetail {  }
}