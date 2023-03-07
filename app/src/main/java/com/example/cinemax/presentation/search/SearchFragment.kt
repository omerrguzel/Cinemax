package com.example.cinemax.presentation.search

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.R
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.wishlist.WishlistModel
import com.example.cinemax.databinding.FragmentSearchBinding
import com.example.cinemax.presentation.adapter.MovieListAdapter
import com.example.cinemax.presentation.detail.DetailFragment
import com.example.cinemax.presentation.home.HomeFragmentDirections
import com.example.cinemax.presentation.home.HomeViewModel
import com.example.cinemax.presentation.searchresult.SearchResultFragmentDirections
import com.example.cinemax.utils.*
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val randomNumber: Int = (0..10).random()
    private var recommendedMovieListAdapter: MovieListAdapter = MovieListAdapter()
    private lateinit var sharedPrefManager: SharedPrefManager
    private var movieId: Int = 500


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefManager = SharedPrefManager(this.requireActivity())
        detectSearchAction(binding.searchBarTextBoxSearchScreen)
        getNowPlayingItem()
        movieId = readIdFromLog()
        getRecommended(movieId, MovieListAdapter.VIEW_TYPE_POPULAR)
        navigateToMovieDetail()
    }

    private fun detectSearchAction(textInputEditText: TextInputEditText) {
        textInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                navigateToSearchResult(textInputEditText)
                true
            } else {
                false
            }
        }
    }

    private fun navigateToSearchResult(textInputEditText: TextInputEditText) {
        val searchQuery = textInputEditText.text.toString().trim()
        if (searchQuery.isNotEmpty()) {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
                    searchQuery
                )
            )
        }
        textInputEditText.setText("")
    }

    private fun getNowPlayingItem() {
        viewModel.getNowPlayingItem().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(ContentValues.TAG, "Fetch Info Error: ${it.message}")
                }
                Resource.Status.SUCCESS -> {
                    val selectedMovieItem = it.data?.movies?.get(randomNumber)
                    Log.d(ContentValues.TAG, "Genres: ${it.data}")
                    binding.apply {
                        progressBar.gone()
                        cardviewNowPlaying.textViewTitle.text = selectedMovieItem?.title ?: ""
                        cardviewNowPlaying.textViewRatingGeneric.text =
                            selectedMovieItem?.voteAverage.toString()
                        cardviewNowPlaying.imageViewGenericPoster.showImage(
                            selectedMovieItem?.posterPath ?: ""
                        )
                        cardviewNowPlaying.textViewReleaseDate.text =
                            selectedMovieItem?.releaseDate?.showOnlyYear()
                        cardviewNowPlaying.root.setOnClickListener {
                            selectedMovieItem?.id?.let { it -> navigateToDetail(it) }
                        }

                    }

                }
            }
        }
    }

    private fun navigateToDetail(id: Int) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                "movie",
                id
            )
        )
    }

    private fun getRecommended(movieId: Int, viewtype: Int) {
        lifecycleScope.launchWhenCreated {
            viewModel.getRecommendations(movieId, viewtype).collectLatest {
                recommendedMovieListAdapter.submitData(it)
                Log.d(ContentValues.TAG, "result : $it")
                recommendedMovieListAdapter.loadStateFlow.collect { loadStates ->
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
//                    binding.errorImageView.isVisible  = loadStates.refresh is LoadState.Error
                }
            }
        }
        binding.recyclerViewRecommended.apply {
            adapter = recommendedMovieListAdapter
            setHasFixedSize(true)
        }
    }

    private fun readIdFromLog(): Int {
        var fetchedLogId = 500
        if (sharedPrefManager.ifContains(RECO) == true) {
            val logData = sharedPrefManager.readLogData(RECO)?.toInt()
            if (logData != null) {
                fetchedLogId = logData
            }
        } else {
            fetchedLogId = 500
        }
        Toast.makeText(context, "$fetchedLogId read from log", Toast.LENGTH_SHORT).show()
        return fetchedLogId
    }

    private fun navigateToMovieDetail(){
        recommendedMovieListAdapter.movieClickListener = { id  ->
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment("movie",id))
        }
    }

    companion object {
        const val RECO = "RECO"
        const val WISHL = "WISHL"
    }
}
