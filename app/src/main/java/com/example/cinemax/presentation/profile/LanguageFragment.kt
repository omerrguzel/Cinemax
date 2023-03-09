package com.example.cinemax.presentation.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentLanguageBinding
import com.example.cinemax.utils.RestartAppDialog
import com.example.cinemax.utils.gone
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LanguageFragment : Fragment() {

    private lateinit var binding : FragmentLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButtonController()
//        Toast.makeText(requireContext(),"default language is ${Locale.getDefault().language}",Toast.LENGTH_SHORT).show()
        detectLanguage()
        setOption()
    }

    private fun backButtonController() {
        binding.buttonBackLanguage.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOption(){
        binding.cardViewEnglish.setOnClickListener {
            val restartAppDialog = RestartAppDialog("en")
            restartAppDialog.show(parentFragmentManager, "RestartAppDialog")
        }
        binding.cardViewTurkish.setOnClickListener {
            val restartAppDialog = RestartAppDialog("tr")
            restartAppDialog.show(parentFragmentManager, "RestartAppDialog")
        }
    }

    private fun detectLanguage(){
        val sharedPreferences = context?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val language = sharedPreferences?.getString("language", null) // default value is "en"
        val defaultLanguage = Locale.getDefault().language
        if(language == "tr"){
            // Current language is Turkish
            binding.checkEnglish.gone()
            binding.cardViewEnglish.isClickable = true
            binding.cardViewTurkish.isClickable = false
        } else if ( language == "en"){
            // Current language is English
            binding.checkTurkish.gone()
            binding.cardViewTurkish.isClickable = true
            binding.cardViewEnglish.isClickable = false
        } else if( language == null) {
            if(defaultLanguage == "tr"){
                binding.checkEnglish.gone()
                binding.cardViewTurkish.isClickable = false
                binding.cardViewEnglish.isClickable = true
            } else if(defaultLanguage=="en"){
                binding.checkTurkish.gone()
                binding.cardViewEnglish.isClickable = false
                binding.cardViewTurkish.isClickable = true
            }
        }
    }

    private fun changeToTurkish(){
        val locale = Locale("tr")
        val resources = context?.resources
        val configuration = resources?.configuration
        configuration?.setLocale(locale)

        val sharedPreferences = context?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putString("language", locale.language)?.commit()
    }

    private fun changeToEnglish(){
        val locale = Locale("tr")
        val resources = context?.resources
        resources?.configuration
        Locale.setDefault(Locale.ENGLISH)

        val sharedPreferences = context?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putString("language", locale.language)?.commit()
    }

}