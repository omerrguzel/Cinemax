package com.example.cinemax.authentication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.cinemax.MainActivity
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentEntryBinding
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EntryFragment : Fragment() {

    private lateinit var binding : FragmentEntryBinding
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(requireContext())
        callbackManager = CallbackManager.Factory.create()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_entry, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        auth = Firebase.auth

        with(binding){
            signUpButton.setOnClickListener {
                findNavController().navigate(R.id.action_entryFragment_to_signUpFragment)
            }
            textViewLoginEntry.setOnClickListener {
                findNavController().navigate(R.id.action_entryFragment_to_loginFragment)
            }
            loginButton.setPermissions("email","public profile")
            loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d(TAG,"Cancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.d(TAG,"Error")
                }
            })
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).
        addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user!=null){
            Toast.makeText(context,"Please sign in to continue.",Toast.LENGTH_SHORT).show()
        }
        else{
            findNavController().navigate(R.id.action_entryFragment_to_homeFragment)
        }
    }
}