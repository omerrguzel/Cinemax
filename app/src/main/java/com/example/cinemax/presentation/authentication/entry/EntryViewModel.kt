package com.example.cinemax.presentation.authentication.entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemax.domain.repository.AuthRepository
import com.example.cinemax.domain.usecase.auth.SignInWithCredentialUseCase
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase
) : ViewModel() {

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private val _showSignInError = MutableLiveData<Unit>()
    val showSignInError: LiveData<Unit>
        get() = _showSignInError

    private val _currentUser = MutableLiveData<FirebaseUser>()
    val currentUser : LiveData<FirebaseUser>
        get() = _currentUser

    fun signInWithFacebook(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        viewModelScope.launch {
            signInWithCredentialUseCase.signInWithCredential(credential)
        }
    }

    fun signInWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        viewModelScope.launch {
            signInWithCredentialUseCase.signInWithCredential(credential)
        }
    }

    fun getCurrentUser() : FirebaseUser? {
         viewModelScope.launch {
             _currentUser.value = signInWithCredentialUseCase.getCurrentUser()
        }
        return _currentUser.value
    }

//    fun firebaseAuthWithFacebook(token: AccessToken) {
//        authRepository.signInWithFacebook(token)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _navigateToHome.value = true
//                } else {
//                    _showSignInError.value = Unit
//                }
//            }
//    }
//
//    fun firebaseAuthWithGoogle(idToken: String) {
//        authRepository.signInWithGoogle(idToken)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _navigateToHome.value = true
//                } else {
//                    _showSignInError.value = Unit
//                }
//            }
//    }
//
//
//

//
//    fun signOutGoogle() {
//        authRepository.signOutGoogle()
//    }
}
