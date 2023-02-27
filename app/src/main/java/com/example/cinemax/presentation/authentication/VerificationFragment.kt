package com.example.cinemax.presentation.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentVerificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {

    private lateinit var binding :FragmentVerificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_verification,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            buttonNextVerifScreen.setOnClickListener {
                findNavController().navigate(R.id.action_verificationFragment_to_newPasswordFragment)
            }
            buttonBackVerifScreen.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}