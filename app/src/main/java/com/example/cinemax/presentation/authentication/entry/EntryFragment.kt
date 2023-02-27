package com.example.cinemax.presentation.authentication.entry

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentEntryBinding
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryFragment : Fragment() {

    private val RC_SIGN_IN = 2
    private lateinit var binding: FragmentEntryBinding
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private val viewModel: EntryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(requireContext())
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_entry, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        callbackManager = CallbackManager.Factory.create()
        val googleSignInClient: GoogleSignInClient by lazy {
            val googleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        }

        with(binding) {
            signUpButton.setOnClickListener {
                findNavController().navigate(R.id.action_entryFragment_to_signUpFragment)
            }
            textViewLoginEntry.setOnClickListener {
                findNavController().navigate(R.id.action_entryFragment_to_loginFragment)
            }
            logoFacebook.setOnClickListener {
                startFacebookSignIn()
            }
            logoGoogle.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
    }

    fun startFacebookSignIn() {
        with(LoginManager.getInstance()) {
            logIn(
                this@EntryFragment,
                listOf("email", "public_profile")
            )
            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d(TAG, "facebook:onSuccess:$loginResult")
                    viewModel.signInWithFacebook(loginResult.accessToken)
                    firebaseAuthWithFacebook(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG, "facebook:onCancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.d(TAG, "Error")
                }
            })
        }
    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                navigateToHome(user)
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    context, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                navigateToHome(null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        startGoogleSignIn(requestCode, data)
    }

    fun startGoogleSignIn(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                viewModel.signInWithGoogle(account)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                Log.d(TAG, e.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    navigateToHome(user)
                    // Handle sign-in success
                } else {

                    // Handle sign-in failure
                }
            }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        viewModel.getCurrentUser()
        viewModel.currentUser.observe(viewLifecycleOwner) {
            navigateToHome(it)
        }
    }

    private fun navigateToHome(user: FirebaseUser?) {
        if (user != null) {
            findNavController().navigate(R.id.action_entryFragment_to_homeFragment)
        } else {
            Toast.makeText(context, "Please sign in to continue.", Toast.LENGTH_SHORT).show()
        }
    }
}
