package com.kodeco.memeverse.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.UUID

data class Post(
    // @DocumentId tells firestore to use this field for the document ID
    @DocumentId
    val authorId: String = "",
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Timestamp? = null,
    val content: String = "",
    val imageUrl: String? = null,
    val userImage: String? = null,
    val isTaggable: Boolean = false,
    val tags: List<String> = emptyList(),
    val likedBy : MutableList<String> = mutableListOf()
){
    val likes: Int
        get() = likedBy.size
}