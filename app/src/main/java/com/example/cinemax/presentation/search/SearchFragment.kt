package com.example.cinemax.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentSearchBinding
import com.google.android.material.textfield.TextInputEditText

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detectSearchAction(binding.searchBarTextBoxSearchScreen)
    }

    private fun detectSearchAction(textInputEditText : TextInputEditText){
        textInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                navigateToSearchResult(textInputEditText)
                true
            } else {
                false
            }
        }
    }

    private fun navigateToSearchResult(textInputEditText : TextInputEditText){
        val searchQuery = textInputEditText.text.toString().trim()
        if(searchQuery.isNotEmpty()){
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(searchQuery))
        }
        textInputEditText.setText("")
    }
}
