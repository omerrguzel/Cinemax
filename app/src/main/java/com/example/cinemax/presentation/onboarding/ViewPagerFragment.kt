package com.example.cinemax.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinemax.databinding.FragmentViewPagerBinding
import com.example.cinemax.onboarding.FirstScreen
import com.example.cinemax.presentation.onboarding.screens.SecondScreen
import com.example.cinemax.presentation.onboarding.screens.ThirdScreen
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : Fragment() {



    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        with(binding){
            viewPager.adapter = adapter
            TabLayoutMediator(pageIndicator,viewPager) {_,_ ->}.attach()
        }
    }
}