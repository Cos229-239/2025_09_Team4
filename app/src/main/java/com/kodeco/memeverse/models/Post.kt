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
    // Private value to store the number of likes
    private val _likedBy: MutableList<String> = mutableListOf()
    val likes: Int
        get() = _likedBy.size
    // Public getter to expose the number of likes outside the class
    val mlikedBy: MutableList<String>
        get() = _likedBy.toMutableList()

    fun likeUnlike(userId: String): Boolean {
        // User has already liked the post, remove them from the list
        if (_likedBy.contains(userId)) {
            _likedBy.remove(userId)
            return false
        } else {
            // User has not liked the post, add them to the list
            _likedBy.add(userId)
            return true
        }
    }
}