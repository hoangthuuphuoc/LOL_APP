package com.example.lol_app.data.reponsitory

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth

    suspend fun createUser(
        email: String, password: String
    ): FirebaseUser? {

        val result = auth.createUserWithEmailAndPassword(
                email, password
            ).await()

        return result.user
    }

    suspend fun loginUser(
        email: String, password: String
    ): FirebaseUser? {

        val result = auth.signInWithEmailAndPassword(
                email, password
            ).await()

        return result.user
    }
}