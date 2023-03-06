package com.example.cinemax.presentation.searchresult

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.example.cinemax.databinding.FragmentSearchResultBinding
import com.example.cinemax.presentation.adapter.SearchResultMediaAdapter
import com.example.cinemax.presentation.adapter.SearchResultPersonAdapter
import com.example.cinemax.presentation.paging.SearchMediaPagingSource
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.observe
import com.example.cinemax.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private val navArgs: SearchResultFragmentArgs by navArgs()
    private var mediaListAdapter: SearchResultMediaAdapter = SearchResultMediaAdapter(SearchResultMediaAdapter.MediaComparator)
    private var personListAdapter: SearchResultPersonAdapter = SearchResultPersonAdapter()
    private val viewModel: SearchResultViewModel by viewModels()


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
        navigateToDetail()

    }

    private fun getSearchMediaResults(searchQuery: String) {

        lifecycleScope.launchWhenCreated {
            viewModel.getSearchMediaResults(searchQuery).collectLatest {
                mediaListAdapter.submitData(it)
                Log.d(ContentValues.TAG, "resultMediaResults : $it")
                mediaListAdapter.loadStateFlow.collect { loadStates ->
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
//                    binding.textViewMovieRelated.isVisible != loadStates.refresh is LoadState.Error

                    if(mediaListAdapter.itemCount == 0 ){
                        binding.textViewMovieRelated.gone()
                    }

//                    val isListEmpty =
//                        mediaListAdapter.itemCount == 0
//                        loadStates.refresh is LoadState.NotLoading && mediaListAdapter.itemCount == 0
//                    checkEmptyMediaList(isListEmpty)
                }
//
//                mediaListAdapter.loadStateFlow.collectLatest { loadStates ->
//
//                }

//
//                mediaListAdapter.addLoadStateListener { loadState ->
//                    if (loadState.source.append is LoadState.NotLoading && loadState.append.endOfPaginationReached) {
//                        if (mediaListAdapter.itemCount == 0)
//                            binding.textViewMovieRelated.gone()
//                        else {
//                            binding.textViewMovieRelated.show()
//                        }
//                    }
//                }
            }
        }
            binding.recyclerViewSearchResult.apply {
                adapter = mediaListAdapter
                setHasFixedSize(true)
        }
    }

    private fun checkEmptyMediaList(isListEmpty : Boolean){
        binding.textViewMovieRelated.isVisible = isListEmpty
    }

    private fun getSearchPersonResults(searchQuery: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.getSearchPersonResults(searchQuery).collectLatest {
                personListAdapter.submitData(it)
                binding.recyclerViewActorSearchResult.smoothScrollToPosition(0)
                Log.d(ContentValues.TAG, "result : $it")
                personListAdapter.loadStateFlow.collect { loadStates ->
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                }
            }
        }
        binding.recyclerViewActorSearchResult.apply {
            adapter = personListAdapter
            setHasFixedSize(true)
        }
    }

    private fun navigateToDetail(){
        mediaListAdapter.mediaClickListener = { mediaItem ->
            findNavController().navigate(SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(mediaItem.mediaType,mediaItem.id))
        }
    }


}