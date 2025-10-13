package com.kodeco.memeverse.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.memeverse.authentication.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val user = authRepository.signIn(email, password)
                onResult(user != null, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }
}