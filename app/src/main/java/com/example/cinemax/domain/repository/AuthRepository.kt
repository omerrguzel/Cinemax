package com.example.cinemax.domain.repository

import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import javax.inject.Inject

interface AuthRepository
{

    //    fun signInWithSelection(string: String, token: AccessToken): Task<AuthResult> {
//        if(string=="facebook"){
//            val credential = FacebookAuthProvider.getCredential(token.token)
//        }else if (string == "google"){
//            val credential = GoogleAuthProvider.getCredential(idToken, null)
//        }
//        return auth.signInWithCredential(credential)
//    }
//
//    fun signInWithFacebook(token: AccessToken): Task<AuthResult> {
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        return auth.signInWithCredential(credential)
//    }
//
//    fun signInWithGoogle(idToken: String): Task<AuthResult> {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        return auth.signInWithCredential(credential)
//    }
    suspend fun signInWithCredential(credential: AuthCredential): Task<AuthResult>

    suspend fun getCurrentUser() : FirebaseUser?
//    fun getCurrentUser(): FirebaseUser? {
//        return auth.currentUser
//    }
//
//    fun signOutGoogle() {
//        googleSignInClient.signOut()
//    }
}