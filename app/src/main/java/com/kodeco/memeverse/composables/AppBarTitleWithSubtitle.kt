package com.kodeco.memeverse.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import com.kodeco.memeverse.ui.theme.MemeVerseTheme

@Composable
fun AppBarTitleWithSubtitle(
    title: String,
    subtitle: String
) {
    MemeVerseTheme {
        Column( horizontalAlignment = Alignment.CenterHorizontally) {
            Text (
                text = title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.surface
            )
            Text (
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}