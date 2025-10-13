package com.kodeco.memeverse.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.memeverse.authentication.AuthRepository
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    fun signup(email: String, password: String, username: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val user = authRepository.createAccount(email, password, username)
                onResult(user != null, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }
}