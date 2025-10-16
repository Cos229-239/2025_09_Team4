package com.kodeco.memeverse.authentication

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.kodeco.memeverse.models.Post
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth
    private val storage = Firebase.storage
    private val database = Firebase.firestore

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun createAccount(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch(e: Exception) {
            Log.e("SignUp", "Something went wrong with sign up. ${e.message}")
        }
    }

    suspend fun signIn(email: String, password: String) {
        try {
            // Use .await() to handle the asynchronous task
            auth.signInWithEmailAndPassword(email, password).await()
        } catch(e: Exception) {
            Log.e("SignIn", "Something went wrong with sign in. ${e.message}")
        }
    }

    // Uploads an image to the Firebase Storage and returns the public download URL
    suspend fun uploadImageToStorage(imageUri: Uri) : String {
        // Create a unique filename for the image
        val fileName = UUID.randomUUID().toString()
        val storageRef = storage.reference.child("posts/$fileName")
        // Upload the image to Firebase Storage
        val uploadTask = storageRef.putFile(imageUri)
        // Await the completion of the task
        uploadTask.await()
        // Return the imageUrl from the firebase storage
        return storageRef.downloadUrl.await().toString()
    }

    // Adds a post to the database
    fun addPost(post: Post) {
        database.collection("posts")
            .add(post)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}