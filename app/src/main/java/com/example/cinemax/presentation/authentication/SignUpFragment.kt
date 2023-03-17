package com.example.cinemax.presentation.authentication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentSignUpBinding
import com.example.cinemax.utils.AuthOperationHelper
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.show
import com.example.cinemax.utils.showSnack
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    @Inject
    lateinit var authOperations: AuthOperationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonBackSignUpScreen.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonSignUpSignUpScreen.setOnClickListener {
                progressBar.show()
                if (checkBoxSignUpScreen.isChecked) {
                    checkEmailAndPassword { fullName, email, password ->
                        authOperations.signUpWithEmailAndPassword(email, password,
                            onSuccess = {
                                view.showSnack(getString(R.string.registration_successful))
                                val user = FirebaseAuth.getInstance().currentUser
                                val profileUpdates = userProfileChangeRequest {
                                    displayName = fullName
                                }
                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "User profile updated.")
                                            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                                        }
                                    }
                            },
                            onFailure = {
                                view.showSnack(it)
                            })
                    }
                } else view.showSnack(getString(R.string.need_to_accept))
                progressBar.gone()
            }
        }
    }

    private fun checkEmailAndPassword(
        onSuccess: (String, String, String) -> Unit
    ) {
        val fullName = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            onSuccess(fullName, email, password)
        } else {
            requireView().showSnack(getString(R.string.fullname_email_password_can_not_be_empty))
        }
    }
}