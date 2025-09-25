package com.kodeco.memeverse.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.memeverse.authentication.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    fun signIn(email: String, password: String, onResult: (isSuccess: Boolean, errorMessage: String?) -> Unit) {
        viewModelScope.launch {
            try {
                authRepository.signIn(email, password)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

}