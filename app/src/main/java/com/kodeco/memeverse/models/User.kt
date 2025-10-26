package com.kodeco.memeverse.models

import com.google.firebase.firestore.DocumentId
import java.util.UUID

data class User(
    @DocumentId
    val id: String = UUID.randomUUID().toString(),
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val username: String = "",
    val avatarUri: String? = null,
    val bio: String? = null,
    val dob: String = "", // Date of Birth
    val matureContent: Boolean = false,
    val visibility: String = "Everyone",
    val postCount: Int = 0,
    val followerCount: Int = 0,
    val followingCount: Int = 0
)