package com.example.cinemax.presentation.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.DialogLogoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LogOutDialog() : DialogFragment() {

    private lateinit var binding: DialogLogoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Objects.requireNonNull(dialog)?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DialogLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        closeDialog()
        logOut()
    }

    private fun closeDialog(){
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun logOut(){
        binding.buttonLogOutDialog.setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_entryFragment)
        }
    }

}