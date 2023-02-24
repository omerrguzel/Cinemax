package com.example.cinemax.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : Fragment() {

    private lateinit var binding : FragmentNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_new_password,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            buttonResetNewPasswordScreen.setOnClickListener {
                findNavController().navigate(R.id.action_newPasswordFragment_to_loginFragment)
            }
            buttonBackNewPasswordScreen.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}