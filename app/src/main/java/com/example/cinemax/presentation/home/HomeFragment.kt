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
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.data.entity.moviedetail.GenreListResponse
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
    private var upComingMovieListAdapter: MovieListAdapter = MovieListAdapter()
    private var popularMovieListAdapter: MovieListAdapter = MovieListAdapter()
    private val viewModel : HomeViewModel by viewModels()
    private val genreAdapter : CategoriesAdapter = CategoriesAdapter(GenreListResponse(arrayListOf()))

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

        getMoviesBySource(binding.recyclerViewUpcoming,upComingMovieListAdapter,"upcoming",
            MovieListAdapter.VIEW_TYPE_UPCOMING
        )
        getMoviesBySource(binding.recyclerViewMostPopular,popularMovieListAdapter,"popular",
            MovieListAdapter.VIEW_TYPE_POPULAR
        )
        getGenres()
    }

    private fun getMoviesBySource(recyclerView: RecyclerView,listAdapter: MovieListAdapter, sourceName: String,viewtype : Int) {
        lifecycleScope.launchWhenCreated {
            viewModel.getMoviesBySource(sourceName = sourceName,viewtype).collectLatest {
                listAdapter.submitData(it)
                Log.d(TAG,"result : $it")
                listAdapter.loadStateFlow.collect { loadStates ->
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    binding.errorImageView.isVisible  = loadStates.refresh is LoadState.Error

                }
            }
        }
        recyclerView.apply {
            adapter = listAdapter
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