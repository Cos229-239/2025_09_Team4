package com.kodeco.memeverse.models

import java.util.UUID

data class User(
    val username: String,
    val password: String,
    val id: String = UUID.randomUUID().toString(),
    val avatarUrl: String? = ""
)