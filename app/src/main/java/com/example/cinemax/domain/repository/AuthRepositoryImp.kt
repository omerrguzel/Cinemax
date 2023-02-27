package com.example.cinemax.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImp(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signInWithCredential(credential: AuthCredential): Task<AuthResult> {
        return firebaseAuth.signInWithCredential(credential)
    }

    override suspend fun getCurrentUser() : FirebaseUser?{
        return firebaseAuth.currentUser
    }
}