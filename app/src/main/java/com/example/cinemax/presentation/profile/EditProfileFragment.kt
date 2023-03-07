package com.example.cinemax.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentEditProfileBinding
import com.example.cinemax.utils.showProfileImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding : FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButtonController()
        setUserView()
    }

    private fun backButtonController() {
        binding.buttonBackEditProfileScreen.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUserView(){
        val auth : FirebaseAuth = Firebase.auth
        binding.apply {
            imageViewProfileImage.showProfileImage(auth.currentUser?.photoUrl.toString())
            textInputEditTextFullName.setText(auth.currentUser?.displayName)
            textViewEmailEditProfScreen.text = auth.currentUser?.email
            textInputEditTextEmail.setText(auth.currentUser?.email)
            textViewProfileName.text = auth.currentUser?.displayName
        }
        auth.languageCode
    }
}