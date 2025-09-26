package com.kodeco.memeverse.composables

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.monitor.FileEntry
import kotlinx.coroutines.launch

//private val FileEntry.uri: Any?
//    get() {
//        TODO()
//    }

@Composable
fun SelectedPhotosAccessSample() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    var files by remember { mutableStateOf(emptyList<FileEntry>()) }

    val storageAccess by produceState(
        initialValue = StorageAccess.Denied,
        context,
        lifecycleOwner,
    ) {
        val eventObserver = LifecycleEventObserver{_, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                value = getStorageAccess(context)

                if(value != StorageAccess.Denied) {
                    coroutineScope.launch {
//                        files = getVisualMedia(context.contentResolver)
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(eventObserver)
        awaitDispose {
            lifecycleOwner.lifecycle.removeObserver(eventObserver)
        }
    }

    val requestPermissions = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        // TODO: Handle when the user denies permission
//        if(storageAccess == StorageAccess.Denied) {
//            coroutineScope.launch {
//                files = getVisualMedia(context.contentResolver)
//            }
//        }
    }

    Column(Modifier.fillMaxSize()) {
        ListItem(
            headlineContent = { Text("Storage Access") },
            trailingContent = { Text(storageAccess.name) },
        )
        HorizontalDivider()
        ListItem(
            headlineContent = { Text("Add files to the selection") },
            trailingContent = {
                if(files.isNotEmpty()) {
                    Text("${files.size} items selected")
                }
            },
            supportingContent = {
                if (storageAccess == StorageAccess.Full) {
                    Text("Access to gallery fully granted")
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            TextButton(
                                onClick = {
                                    requestPermissions.launch(
                                        arrayOf(
                                            READ_MEDIA_IMAGES,
                                            READ_MEDIA_VISUAL_USER_SELECTED
                                        )
                                    )
                                },
                            ) {
                                Text("Images")
                            }
                            TextButton(
                                onClick = {
                                    requestPermissions.launch(
                                        arrayOf(
                                            READ_MEDIA_VIDEO,
                                            READ_MEDIA_VISUAL_USER_SELECTED
                                        )
                                    )
                                },
                            ) {
                                Text("Videos")
                            }
                            TextButton(
                                onClick = {
                                    requestPermissions.launch(
                                        arrayOf(
                                                    READ_MEDIA_IMAGES,
                                                    READ_MEDIA_VIDEO,
                                                    READ_MEDIA_VISUAL_USER_SELECTED
                                        )
                                    )
                                },
                            ) {
                                Text("Both")
                            }
                        }
                    } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            TextButton(
                                onClick = { requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES)) }
                            ) {
                                Text("Images")
                            }
                            TextButton(
                                onClick = {
                                    requestPermissions.launch(
                                        arrayOf(READ_MEDIA_VIDEO)
                                    )
                                },
                            ) {
                                Text("Videos")
                            }
                            TextButton(
                                onClick = {
                                    requestPermissions.launch(
                                        arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO)
                                    )
                                },
                            ) {
                                Text("Both")
                            }
                        }
                    } else {
                       TextButton(
                           onClick = {
                               requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
                           },
                       ) {
                           Text("Request full gallery access")
                       }
                    }
                }
            },
        )
        HorizontalDivider()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            items(files) { file ->
//                AsyncImage(
//                    model = file.uri,
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .aspectRatio(1f),
//
//                )
            }
        }
    }

}

private suspend fun getVisualMedia(contentResolver: ContentResolver) {
    TODO("Not yet implemented")
}

private fun getStorageAccess(context: Context): StorageAccess {
    return if (
        checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED ||
        checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED
    ) {
        // Full access on android 13+
        StorageAccess.Full
    } else if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED) == PERMISSION_GRANTED
    ) {
        StorageAccess.Partial
    } else if (checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
        // Full access up to Android 12
        StorageAccess.Full
    } else {
        // Access Denied
        StorageAccess.Denied
    }
}

private enum class StorageAccess {
    Full, Partial, Denied
}