package com.example.cinemax.presentation.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.R
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.databinding.FragmentHomeBinding
import com.example.cinemax.presentation.adapter.CategoriesAdapter
import com.example.cinemax.presentation.adapter.MovieListAdapter
import com.example.cinemax.utils.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var upComingMovieListAdapter: MovieListAdapter = MovieListAdapter()
    private var popularMovieListAdapter: MovieListAdapter = MovieListAdapter()
    private var topRatedMovieListAdapter : MovieListAdapter = MovieListAdapter()
    private val viewModel : HomeViewModel by viewModels()
    private val genreAdapter : CategoriesAdapter = CategoriesAdapter(arrayListOf())

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

        detectSearchAction(binding.textViewSearchTextHomeScreen)
        navigateToMovieDetail()
        navigateToWishList()
        setRvAdapters()
        setUserData()



    }

    private fun setUserData(){
        val auth : FirebaseAuth = Firebase.auth
        binding.imageViewProfile.showProfileImage(auth.currentUser?.photoUrl.toString())
        println("UserProfilePic ${auth.currentUser?.photoUrl.toString()}:" )

        val firstName = auth.currentUser?.displayName.toString().substringBefore(" ")
        val helloString = getString(R.string.hello)

        binding.textViewHelloHome.text = "$helloString $firstName"
    }

    private fun setRvAdapters(){
        getMoviesBySource(binding.recyclerViewUpcoming,upComingMovieListAdapter,"upcoming",
            MovieListAdapter.VIEW_TYPE_UPCOMING,null
        )
        getMoviesBySource(binding.recyclerViewMostPopular,popularMovieListAdapter,"popular",
            MovieListAdapter.VIEW_TYPE_POPULAR,null
        )
        getMoviesBySource(binding.recyclerViewTopRated,topRatedMovieListAdapter,"top_rated",
            MovieListAdapter.VIEW_TYPE_POPULAR,null)
        getGenres()

        genreAdapter.filterMoviesByGenre = {
            getMoviesBySource(binding.recyclerViewMostPopular,popularMovieListAdapter,"popular",
                MovieListAdapter.VIEW_TYPE_POPULAR,it
            )
            getMoviesBySource(binding.recyclerViewTopRated,topRatedMovieListAdapter,"top_rated",
                MovieListAdapter.VIEW_TYPE_POPULAR,it)
        }
    }

    private fun getMoviesBySource(recyclerView: RecyclerView,listAdapter: MovieListAdapter, sourceName: String,viewtype : Int,genreId : Int?) {
        lifecycleScope.launchWhenCreated {
            viewModel.getMoviesBySource(sourceName = sourceName,viewtype,genreId).collectLatest {
                listAdapter.submitData(it)
                binding.recyclerViewMostPopular.smoothScrollToPosition(0)
                Log.d(TAG,"result : $it")
                listAdapter.loadStateFlow.collect { loadStates ->
                    binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                    binding.errorImageView.isVisible  = loadStates.refresh is LoadState.Error
                    if(sourceName == "top_rated" && listAdapter.itemCount == 0){
                        binding.textViewTitleTopRated.gone()
                    }
                    else binding.textViewTitleTopRated.show()
                }
            }
        }
        recyclerView.apply {
            adapter = listAdapter
            setHasFixedSize(true)
        }
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
                    val list : MutableList<GenreResponse>? = it.data?.genres?.toMutableList()
                    list?.add(0,GenreResponse(name = getString(R.string.all)))
                    genreAdapter.setData(list?.toList())
                    binding.recyclerViewCategories.adapter = genreAdapter
                }
            }
        }
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
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchResultFragment(searchQuery))
        }
        textInputEditText.setText("")
    }

    private fun navigateToMovieDetail(){
        upComingMovieListAdapter.movieClickListener = { id  ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment("movie",id))
        }
        popularMovieListAdapter.movieClickListener = { id  ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment("movie",id))
        }
        topRatedMovieListAdapter.movieClickListener = { id  ->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment("movie",id))
        }
    }

    private fun navigateToWishList(){
        binding.imageViewWishListHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_wishlistFragment)
        }
    }
}