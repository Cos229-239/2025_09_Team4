package com.kodeco.memeverse.screens.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddViewModel: ViewModel() {
    // Private value to hold the selected image URI
    private val _imageUri = MutableStateFlow<Uri?>(null)
    // Expose the selected image URI as a StateFlow
    var imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    // Updates the Uri when an image is selected
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
}