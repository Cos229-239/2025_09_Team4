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
import com.kodeco.memeverse.models.User
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth
    private val storage = Firebase.storage
    private val database = Firebase.firestore

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun createAccount(email: String, password: String, username: String) {
        // Use firebase to create a user and await the response
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        // Get the user from the authResult
        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            // User is successfully created with firebase
            val newUser = User(
                id = firebaseUser.uid,
                username = username,
                email = email
            )
            // Save the user object to the users database
            database.collection("users")
                .document(firebaseUser.uid)
                .set(newUser)
                .await()
        } else {
            throw IllegalStateException("Firebase User is null after creating an account")
        }
    }

    suspend fun signIn(email: String, password: String) {
        // Log in with firebase and await the response
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
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
    suspend fun addPost(post: Post) {
        database.collection("posts")
            .add(post)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    // Load the posts from the database
    suspend fun loadPosts(onPostsLoaded: (List<Post>) -> Unit) {
        try {
            val postCollection = database.collection("posts")
                .get()
                .await()
            val posts = postCollection.toObjects(Post::class.java)
            val postsWithUsername = posts.map { post ->
                coroutineScope {
                    if (post.authorId.isNotEmpty()) {
                        try {
                            val userDocument = database.collection("users")
                                .document(post.authorId)
                                .get()
                                .await()
                            post.username = userDocument.getString("username") ?: "Unknown"
                        } catch (e: Exception) {
                            Log.w("AuthRepository", "Error getting documents: ${e}")
                            post.username = "Unknown"
                        }
                    }
                    // Return the modified post object
                    post
                }
            }
            onPostsLoaded(postsWithUsername)

        } catch (e: Exception) {
            Log.w("AuthRepository", "Error getting documents: ${e}")
            emptyList<Post>()
        }
//            val postCollectionRef = database.collection("posts")
//                .get()
//                .addOnSuccessListener { documents ->
//                    val posts = mutableListOf<Post>()
//                    for (document in documents ) {
//                        // Convert the document to a Post object
//                        val post = document.toObject(Post::class.java)
//                        // Set the document id
//                        post.id = document.id
//                        // Add each post to the list
//                        posts.add(post)
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                    }
//                    onPostsLoaded(posts)
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("AuthRepository", "Error getting documents: ${exception}")
//                }
        }
}