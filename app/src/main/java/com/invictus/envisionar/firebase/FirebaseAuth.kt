package com.invictus.envisionar.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthManager {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Sign Up Function
    suspend fun signUp(email: String, password: String): FirebaseUser? {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user // Returns the signed-up user
        } catch (e: Exception) {
            println("Error signing up: ${e.message}")
            null
        }
    }

    // Login Function
    suspend fun login(email: String, password: String): Boolean {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user != null // Returns true if login is successful
        } catch (e: Exception) {
            println("Error logging in: ${e.message}")
            false
        }
    }

    // Logout Function
    fun logout() {
        firebaseAuth.signOut()
    }

    // Get Current User
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}
