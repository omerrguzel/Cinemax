package com.example.cinemax.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentLoginBinding
import com.example.cinemax.utils.AuthOperationHelper
import com.example.cinemax.utils.showSnack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var authOperations: AuthOperationHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonLoginLoginScreen.setOnClickListener {
                checkEmailAndPassword { email, password ->
                    authOperations.signInWithEmailAndPassword(email, password,
                        onSuccess = {
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        },
                        onFailure = {
                            requireView().showSnack(it)
                        })
                }
            }
            textViewForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
            }
            buttonBackLoginScreen.setOnClickListener {
                findNavController().popBackStack()
            }

        }
    }


    private fun checkEmailAndPassword(
        onSuccess: (String, String) -> Unit
    ) {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            onSuccess(email, password)
        } else {
            requireView().showSnack(getString(R.string.fullname_email_password_can_not_be_empty))
        }
    }
}