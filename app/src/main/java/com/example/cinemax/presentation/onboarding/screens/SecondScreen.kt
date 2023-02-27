package com.example.cinemax.presentation.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentFirstScreenBinding
import com.example.cinemax.databinding.FragmentSecondScreenBinding

class SecondScreen : Fragment() {

    private lateinit var binding :FragmentSecondScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_second_screen, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        binding.buttonNextOnboarding.setOnClickListener {
            viewPager?.currentItem=2
        }
    }
}