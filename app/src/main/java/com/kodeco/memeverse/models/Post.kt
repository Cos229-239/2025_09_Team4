package com.kodeco.memeverse.models

import java.util.UUID

data class Post(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val imageUrl: String? = null,
    val isTaggable: Boolean = false,
    val tags: List<String> = listOf(),
    private val _likedBy: MutableList<String> = mutableListOf()
){
    val likes: Int
        get() = _likedBy.size

    // Public read-only access
    val likedBy: List<String>
        get() = _likedBy.toList()

    fun like(userId: String): Boolean {
        return if (!_likedBy.contains(userId)) {
            _likedBy.add(userId)
            true
        } else {
            false
        }
    }

    fun unlike(userId: String): Boolean {
        return if (_likedBy.contains(userId)) {
            _likedBy.remove(userId)
            true
        } else {
            false
        }
    }
}