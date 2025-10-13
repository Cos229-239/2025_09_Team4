package com.kodeco.memeverse.models


import java.util.UUID

data class User(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val id: String = UUID.randomUUID().toString(),
    val avatarUri: String? = null
)