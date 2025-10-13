package com.kodeco.memeverse.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.kodeco.memeverse.models.User
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun createAccount(email: String, password: String, username: String): FirebaseUser? {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = result.user

        firebaseUser?.let {
            val user = User(
                email = email,
                password = password,
                username = username,
                id = it.uid
            )
            firestore.collection("users").document(it.uid).set(user).await()
        }

        return firebaseUser
    }

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user
    }

    suspend fun fetchUser(uid: String): User? {
        val snapshot = firestore.collection("users").document(uid).get().await()
        return snapshot.toObject(User::class.java)
    }

    fun signOut() {
        auth.signOut()
    }
}