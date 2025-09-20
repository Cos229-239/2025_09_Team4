package com.kodeco.memeverse.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _users = mutableMapOf<String, String>()
    private val _authStatus = MutableStateFlow(false)
    val authStatus: StateFlow<Boolean> = _authStatus

    fun signup(username: String, password: String): Boolean {
        if (_users.containsKey(username)) return false
        _users[username] = password
        return true
    }

    fun login(username: String, password: String): Boolean {
        val success = _users[username] == password
        _authStatus.value = success
        return success
    }
}