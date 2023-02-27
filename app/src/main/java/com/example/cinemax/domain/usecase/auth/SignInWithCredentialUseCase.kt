package com.example.cinemax.domain.usecase.auth

import com.example.cinemax.domain.repository.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInWithCredentialUseCase @Inject constructor (
    private val authRepository: AuthRepository
        ) {

    suspend fun signInWithCredential(credential : AuthCredential) : Task<AuthResult> {
        return authRepository.signInWithCredential(credential)
    }

    suspend fun getCurrentUser() : FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}