package com.kodeco.memeverse.authentication

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun createAccount(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch(e: Exception) {
            Log.e("SignUp", "Something went wrong with sign up. ${e.message}")
        }
    }

    suspend fun signIn(email: String, password: String): AuthResult? {
        return auth.signInWithEmailAndPassword(email, password).await()
    }
}