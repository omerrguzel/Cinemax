package com.example.cinemax.presentation.searchresult

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.cinemax.databinding.FragmentSearchResultBinding
import com.example.cinemax.presentation.adapter.SearchResultMediaAdapter
import com.example.cinemax.presentation.adapter.SearchResultPersonAdapter
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding : FragmentSearchResultBinding
    private val navArgs: SearchResultFragmentArgs by navArgs()
    private var mediaListAdapter : SearchResultMediaAdapter = SearchResultMediaAdapter()
    private var personListAdapter : SearchResultPersonAdapter = SearchResultPersonAdapter()
    private val viewModel : SearchResultViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBarTextBoxSearchResultScreen.setText(navArgs.searchQuery)
        getSearchPersonResults(navArgs.searchQuery)
        getSearchMediaResults(navArgs.searchQuery)

    }

    private fun getSearchMediaResults(searchQuery : String){
        lifecycleScope.launchWhenCreated {
            viewModel.getSearchMediaResults(searchQuery).collectLatest {
                mediaListAdapter.submitData(it)
                binding.recyclerViewSearchResult.smoothScrollToPosition(0)
                Log.d(ContentValues.TAG,"result : $it")
                mediaListAdapter.loadStateFlow.collect{ loadStates->
                    TODO()
                }
            }
        }
        binding.recyclerViewSearchResult.apply {
            adapter = mediaListAdapter
            setHasFixedSize(true)
        }
    }

    private fun getSearchPersonResults(searchQuery : String){
        lifecycleScope.launchWhenCreated {
            viewModel.getSearchPersonResults(searchQuery).collectLatest {
                personListAdapter.submitData(it)
                binding.recyclerViewActorSearchResult.smoothScrollToPosition(0)
                Log.d(ContentValues.TAG,"result : $it")
                personListAdapter.loadStateFlow.collect{ loadStates->
                    TODO()
                }
            }
        }
        binding.recyclerViewActorSearchResult.apply {
            adapter = personListAdapter
            setHasFixedSize(true)
        }
    }
}