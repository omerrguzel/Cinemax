package com.example.cinemax.presentation.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemax.R
import com.example.cinemax.data.entity.moviedetail.GenreList
import com.example.cinemax.databinding.FragmentHomeBinding
import com.example.cinemax.presentation.adapter.CategoriesAdapter
import com.example.cinemax.presentation.adapter.MovieListAdapter
import com.example.cinemax.utils.Resource
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var movieListAdapter: MovieListAdapter = MovieListAdapter()
    private val viewModel : HomeViewModel by viewModels()
    private val genreAdapter : CategoriesAdapter = CategoriesAdapter(GenreList(arrayListOf()))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMoviesBySource("upcoming")
        getGenres()
    }

    private fun getMoviesBySource(sourceName: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.getMoviesBySource(sourceName = sourceName).collectLatest {
                movieListAdapter.submitData(it)
                Log.d(TAG,"result : $it")
                movieListAdapter.loadStateFlow.collect { loadStates ->
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    binding.errorImageView.isVisible  = loadStates.refresh is LoadState.Error
//                    binding.chipGroupSources.isInvisible = loadStates.refresh is LoadState.Error

                }
            }
        }
        binding.recyclerViewUpcoming.apply {
            adapter = movieListAdapter
            setHasFixedSize(true)
        }
//        adapter.setMovieOnClickListener(
//            object : IMovieClickListener {
//                override fun onClick(moviesItem: MoviesItem) {
//                    val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(moviesItem.id)
//                    findNavController().navigate(action)
//                }
//            })
    }

    private fun getGenres() {
        viewModel.getGenres().observe(viewLifecycleOwner){
            when(it.status){
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(TAG,"Fetch Info Error: ${it.message}")
                }
                Resource.Status.SUCCESS -> {
                    Log.d(TAG,"Genres: ${it.data}")
                    binding.progressBar.gone()
                    genreAdapter.setData(it.data)
                    binding.recyclerViewCategories.adapter = genreAdapter
                }
            }
        }
    }
}