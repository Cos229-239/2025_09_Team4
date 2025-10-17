package com.kodeco.memeverse.models

import com.google.firebase.firestore.DocumentId
import java.util.UUID

data class User(
    @DocumentId
    val uid: String = "",
    val username: String = "",
    val password: String = "",
    val id: String = UUID.randomUUID().toString(),
    val avatarUrl: String? = ""
)