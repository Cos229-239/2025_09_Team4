package com.kodeco.memeverse.screens.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.memeverse.authentication.AuthRepository
import com.kodeco.memeverse.models.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val repository = AuthRepository()
    private val _posts = MutableStateFlow<List<Post>>(emptyList())

    val posts = _posts

    fun getPosts() {
        viewModelScope.launch {
            try {
                 repository.loadPosts{ posts ->
                     posts.forEach { post ->
                         Log.d("FeedViewModel", "Post: ${post.content}")
                     }
                     _posts.value = posts
                     Log.d("FeedViewModel", "Posts loaded in the amount of: ${_posts.value.size}")
                 }
            } catch (e: Exception) {
                    Log.w("FeedViewModel", "Error loading posts: ${e.message}")
            }
        }
    }
}