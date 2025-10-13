package com.kodeco.memeverse.models

import coil3.Uri
import java.util.UUID

//User Comment
data class Comment(
    val id: String = UUID.randomUUID().toString(),
    val postID: String = "",
    val userID: String = "",
    val username: String = "",
    val userAvatarUri: String? = null,
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
){
    //Constructor to create Comment User Object
    constructor(postID: String, user: User, content: String) : this(
        postID = postID,
        userID  = user.id,
        username = user.username,
        userAvatarUri = user.avatarUri,
        content = content
    )
}
