package com.kodeco.memeverse.models

import java.util.UUID

data class Post(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val imageUrl: String? = null,
    val isTaggable: Boolean = false,
    val tags: List<String> = listOf(),
    val likedBy : MutableList<String> = mutableListOf()
){
    val likes: Int
        get() = likedBy.size
}