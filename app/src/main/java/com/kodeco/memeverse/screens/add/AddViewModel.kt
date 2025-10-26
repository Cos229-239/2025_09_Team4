package com.kodeco.memeverse.screens.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.memeverse.authentication.AuthRepository
import com.kodeco.memeverse.models.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddViewModel: ViewModel() {
    // Private value to hold the selected image URI
    private val _imageUri = MutableStateFlow<Uri?>(null)
    // Expose the selected image URI as a StateFlow
    var imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()
    // Create an instance of the repository
    private val repository = AuthRepository()

    // Updates the Uri when an image is selected
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
    // Uploads an image to the Firebase Storage and returns the public download URL
    fun createPost(post: Post, imageUri: Uri, onSuccess: () -> Unit) {
        // Launch a coroutine thread to upload the image
        viewModelScope.launch {
            try {
                // Upload the image to Firebase Storage
                val imageUrl = repository.uploadImageToStorage(imageUri)
                // Update the post with the image URL
                val updatedPost = post.copy(imageUrl = imageUrl)
                // Add the post to the database
                repository.addPost(updatedPost)
                onSuccess()
            } catch (e: Exception) {
                Log.e("AddViewModel", "Error uploading image", e)
            }
        }
    }
}