package com.kodeco.memeverse.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.memeverse.authentication.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    fun createAccount(email: String, password: String, username: String, onResult: (isSuccess: Boolean, errorMessage: String?)-> Unit) {
        viewModelScope.launch {
            try {
                val authResult = authRepository.createAccount(email, password, username)
                onResult(true, null)
            } catch (e: Exception) {
                // Handle error
                onResult(false, e.message)
            }
        }
    }

}