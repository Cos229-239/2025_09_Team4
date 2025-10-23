package com.kodeco.memeverse.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.UUID

data class Post(
    // @DocumentId tells firestore to use this field for the document ID
    @DocumentId
    var id: String = UUID.randomUUID().toString(),
    val authorId: String = "",
    val timestamp: Timestamp? = Timestamp.now(),
    val content: String = "",
    val imageUrl: String? = null,
    val userImage: String? = null,
    val isTaggable: Boolean = false,
    val tags: List<String> = emptyList(),
    val likedBy : MutableList<String> = mutableListOf(),
    @get:Exclude
    var username: String = ""
){
    val likes: Int
        get() = likedBy.size
}