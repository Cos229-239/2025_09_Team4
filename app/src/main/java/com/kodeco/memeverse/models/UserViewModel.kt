package com.kodeco.memeverse.models

import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var user: User? = null
        private set

    fun setUser(loggedInUser: User) {
        user = loggedInUser
    }

    fun updateUser(username: String, avatarUri: String) {
        user = user?.copy(username = username, avatarUri = avatarUri)
    }
}