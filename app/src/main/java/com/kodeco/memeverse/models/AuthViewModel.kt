package com.kodeco.memeverse.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val firebaseUser: StateFlow<FirebaseUser?> = _firebaseUser

    private val _authStatus = MutableStateFlow(false)
    val authStatus: StateFlow<Boolean> = _authStatus

    init {
        restoreSession()
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _firebaseUser.value = user
            _authStatus.value = user != null

            if (user != null) {
                Log.d("AuthViewModel", "AuthStateListener: User logged in: ${user.email}")
                fetchUser(user.uid)
            } else {
                Log.d("AuthViewModel", "AuthStateListener: No user logged in")
                _currentUser.value = null
            }
        }
    }

    private fun restoreSession() {
        val user = auth.currentUser
        _firebaseUser.value = user
        _authStatus.value = user != null

        if (user != null) {
            Log.d("AuthViewModel", "Session restored: ${user.email}")
            fetchUser(user.uid)
        } else {
            Log.d("AuthViewModel", "Session restoration failed: No user")
        }
    }

    fun signup(email: String, password: String, username: String) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user ?: return@launch

                val user = User(
                    email = email,
                    username = username,
                    id = firebaseUser.uid
                )

                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(user)
                    .await()

                _currentUser.value = user
                _authStatus.value = true
                Log.d("AuthViewModel", "Signup successful: ${user.email}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Signup failed: ${e.message}")
                _authStatus.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user ?: return@launch
                fetchUser(firebaseUser.uid)
                _authStatus.value = true
                Log.d("AuthViewModel", "Login successful: ${firebaseUser.email}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login failed: ${e.message}")
                _authStatus.value = false
                _currentUser.value = null
            }
        }
    }

    private fun fetchUser(uid: String) {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("users")
                    .document(uid)
                    .get()
                    .await()

                if (snapshot.exists()) {
                    val user = snapshot.toObject(User::class.java)
                    _currentUser.value = user
                    Log.d("AuthViewModel", "User fetched from Firestore: ${user?.email}")
                } else {
                    Log.w("AuthViewModel", "User document not found for UID: $uid")
                    _currentUser.value = null
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Fetch user failed: ${e.message}")
                _currentUser.value = null
            }
        }
    }

    fun updateProfile(newUsername: String, avatarUri: String?) {
        val user = _currentUser.value ?: return
        val updated = user.copy(username = newUsername, avatarUri = avatarUri)

        viewModelScope.launch {
            try {
                firestore.collection("users")
                    .document(user.id)
                    .set(updated)
                    .await()

                _currentUser.value = updated
                Log.d("AuthViewModel", "Profile updated: ${updated.username}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Update profile failed: ${e.message}")
            }
        }
    }

    fun logout() {
        auth.signOut()
        _authStatus.value = false
        _currentUser.value = null
        _firebaseUser.value = null
        Log.d("AuthViewModel", "User logged out")
    }
}