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
                ensureUserDocument(user)
                listenToUser(user.uid)
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
            ensureUserDocument(user)
            listenToUser(user.uid)
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
                ensureUserDocument(firebaseUser)
                listenToUser(firebaseUser.uid)
                _authStatus.value = true
                Log.d("AuthViewModel", "Login successful: ${firebaseUser.email}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login failed: ${e.message}")
                _authStatus.value = false
                _currentUser.value = null
            }
        }
    }

    private fun listenToUser(uid: String) {
        firestore.collection("users")
            .document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || !snapshot.exists()) {
                    Log.e("AuthViewModel", "Real-time listener error: ${error?.message}")
                    _currentUser.value = null
                    return@addSnapshotListener
                }

                val user = snapshot.toObject(User::class.java)
                _currentUser.value = user
                Log.d("AuthViewModel", "Real-time update received: ${user?.username}")
            }
    }

    private fun ensureUserDocument(firebaseUser: FirebaseUser) {
        viewModelScope.launch {
            try {
                val docRef = firestore.collection("users").document(firebaseUser.uid)
                val snapshot = docRef.get().await()

                if (!snapshot.exists()) {
                    val newUser = User(
                        email = firebaseUser.email ?: "Anonymous",
                        username = firebaseUser.displayName ?: "",
                        id = firebaseUser.uid,
                        avatarUri = firebaseUser.photoUrl?.toString(),
                        bio = "Welcome to Memeverse! Update your bio",
                        postCount = 0,
                        followerCount = 0,
                        followingCount = 0
                    )

                    docRef.set(newUser).await()
                    Log.d("AuthViewModel", "User document created for UID: ${firebaseUser.uid}")
                } else {
                    Log.d("AuthViewModel", "User document already exists for UID: ${firebaseUser.uid}")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Failed to ensure user document: ${e.message}")
            }
        }
    }

    fun updateProfile(newUsername: String, avatarUri: String?, newBio: String?, dob: String, matureContent: Boolean, visibility: String, name: String?) {
        val user = _currentUser.value ?: return
        val updated = user.copy(username = newUsername, avatarUri = avatarUri, bio = newBio, dob = dob, matureContent = matureContent, visibility = visibility)

        viewModelScope.launch {
            try {
                firestore.collection("users")
                    .document(user.id)
                    .set(updated)
                    .await()

                listenToUser(updated.id)
                Log.d("AuthViewModel", "Profile updated: ${updated.username}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Update profile failed: ${e.message}")
            }
        }
    }

    fun deleteAccount() {
        val firebaseUser = auth.currentUser ?: return
        val userId = firebaseUser.uid

        viewModelScope.launch {
            try {
                // Delete Firestore user document
                firestore.collection("users")
                    .document(userId)
                    .delete()
                    .await()

                // Delete Firebase Auth account
                firebaseUser.delete().await()

                // Clear local state
                _currentUser.value = null
                _firebaseUser.value = null
                _authStatus.value = false

                Log.d("AuthViewModel", "Account deleted for UID: $userId")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Delete account failed: ${e.message}")
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