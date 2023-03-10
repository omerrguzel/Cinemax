package com.example.cinemax.presentation.detail

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cinemax.R
import com.example.cinemax.data.entity.wishlist.WishlistModel
import com.example.cinemax.databinding.FragmentDetailBinding
import com.example.cinemax.presentation.adapter.CrewAdapter
import com.example.cinemax.presentation.adapter.EpisodeAdapter
import com.example.cinemax.presentation.seasons.SeasonDialog
import com.example.cinemax.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.fixedRateTimer

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val navArgs: DetailFragmentArgs by navArgs()
    private var crewAdapter: CrewAdapter = CrewAdapter(arrayListOf())
    private var episodeAdapter: EpisodeAdapter = EpisodeAdapter(arrayListOf())
    private var wishList: MutableList<WishlistModel> = mutableListOf()
    private lateinit var sharedPrefManager: SharedPrefManager
    private var selectedWishlistModel = WishlistModel()
    private val seasonDialog: SeasonDialog = SeasonDialog()
    private val args = Bundle()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefManager = SharedPrefManager(this.requireActivity())
        backButtonController()
        initDetailMedia()
        navigateToVideo()
        openSeasonDialog()
        selectedSeasonListener()
    }


    private fun initDetailMedia() {
        if (navArgs.mediaType == "tv") {
            getTVDetailResult(navArgs.id)
        } else {
            getMovieDetails(navArgs.id)
            saveLogToShared(navArgs.id)
        }
        setFavButton()
    }

    private fun getTVDetailResult(id: Int) {
        viewModel.getTVDetailResult(id).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(TAG, "Fetch Info Error: ${it.message}")
                }
                Resource.Status.SUCCESS -> {
                    Log.d(ContentValues.TAG, "TvDetailResult: ${it.data}")
                    selectedWishlistModel.id = it.data?.id
                    selectedWishlistModel.title = it.data?.tvName
                    selectedWishlistModel.voteAverage = it.data?.voteRating
                    selectedWishlistModel.backdropPath = it.data?.backdropPath
                    selectedWishlistModel.mediaType = "TV"
                    selectedWishlistModel.genre = it.data?.genres?.get(0)?.name
                    initFavButtonColor()
                    binding.progressBar.gone()
                    binding.apply {
                        textViewTitleDetail.text = it.data?.tvName
                        textViewOverview.text = it.data?.overview
                        if (it.data?.genres?.size != 0) textViewGenreDetail.text =
                            it.data?.genres?.get(0)?.name.toString()
                        textViewRatingDetail.text = it.data?.voteRating?.roundRating()
                        textViewSeasonNumber.text = it.data?.seasonInfoList?.get(0)?.name
                        imageViewBackgroundDetailPoster.showImage(it.data?.posterPath ?: "null")
                        imageViewForegroundDetailPoster.showImage(it.data?.posterPath ?: "null")
                        if (it.data?.runTime?.isEmpty() == true) {
                            textViewDuration.text = ""
                        } else textViewDuration.text = it.data?.runTime?.get(0).toString()
                    }
                    getSeasonDetails(
                        navArgs.id,
                        it.data?.seasonInfoList?.get(0)?.seasonNumber ?: 0,
                        it.data?.seasonInfoList?.get(0)?.name
                    )
                }
            }
        }
    }

    private fun getSeasonDetails(id: Int, seasonNumber: Int, name: String?) {
        viewModel.getSeasonDetails(id, seasonNumber).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(TAG, "Fetch Info Error: ${it.message}")
                }
                Resource.Status.SUCCESS -> {
                    Log.d(ContentValues.TAG, "TvDetailResult: ${it.data}")
                    binding.progressBar.gone()
                    binding.apply {
                        textViewReleaseDateDetail.text = it.data?.airDate?.showOnlyYear()
                        if (it.data?.posterPath != null) {
                            imageViewBackgroundDetailPoster.showImage(it.data.posterPath ?: "null")
                            imageViewForegroundDetailPoster.showImage(it.data.posterPath ?: "null")
                            if (it.data.overview != "") {
                                textViewOverview.text = it.data.overview
                            }
                            println("Episode List" + it.data.episodeList)
                        }
                        crewAdapter.setData(it.data?.episodeList?.get(0)?.crewList)
                        episodeAdapter.setData(it.data?.episodeList)
                        recyclerViewCastAndCrew.adapter = crewAdapter
                        recyclerViewEpisodes.adapter = episodeAdapter
                        textViewSeasonNumber.text = name
                        if (it.data?.episodeList?.get(0)?.crewList?.isEmpty() == true) {
                            recyclerViewCastAndCrew.gone()
                            textViewCastTitle.gone()
                        }
                    }
                }
            }
        }
    }

    private fun selectedSeasonListener() {
        seasonDialog.passSelectedSeasonListener = {
            getSeasonDetails(navArgs.id, it?.seasonNumber ?: 0, it?.name)
            it?.seasonNumber?.let { seasonNumber -> args.putInt("LAST_SEASON_NUMBER", seasonNumber) }
        }
    }

    private fun getMovieDetails(id: Int) {
        viewModel.getMovieDetails(id).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(ContentValues.TAG, "Fetch Info Error: ${it.message}")
                }
                Resource.Status.SUCCESS -> {

                    selectedWishlistModel.id = it.data?.id
                    selectedWishlistModel.title = it.data?.movieName
                    selectedWishlistModel.voteAverage = it.data?.voteRating
                    selectedWishlistModel.backdropPath = it.data?.backdropPath
                    selectedWishlistModel.mediaType = "Movie"
                    selectedWishlistModel.genre = it.data?.genres?.get(0)?.name
                    initFavButtonColor()
                    Log.d(ContentValues.TAG, "TvDetailResult: ${it.data}")
                    binding.progressBar.gone()
                    binding.apply {
                        textViewTitleDetail.text = it.data?.movieName
                        textViewReleaseDateDetail.text = it.data?.releaseDate?.showOnlyYear()
                        textViewDuration.text = it.data?.runtime?.toString()
                        if (it.data?.genres?.size != 0) textViewGenreDetail.text =
                            it.data?.genres?.get(0)?.name.toString()
                        imageViewBackgroundDetailPoster.showImage(it.data?.posterPath ?: "")
                        imageViewForegroundDetailPoster.showImage(it.data?.posterPath ?: "")
                        textViewOverview.makeExpandable(it.data?.overview)
                        textViewRatingDetail.text = it.data?.voteRating?.roundRating()
                        textViewCastTitle.gone()
                        textViewEpisodeTitle.gone()
                        textViewSeasonNumber.gone()
                    }
                }
            }
        }
    }

    private fun initFavButtonColor() {
        if (checkIfInWishList()) {
            binding.imageViewWishDetail.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.wishlist_home
                )
            )
        } else {
            binding.imageViewWishDetail.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.white_heart
                )
            )
        }
    }

    private fun setFavButton() {
        binding.imageViewWishDetail.setOnClickListener {
            if (isSelected()) {
                removeWishlist()
                initFavButtonColor()
            } else {
                saveWishlistToShared()
                initFavButtonColor()
            }
        }
    }

    private fun backButtonController() {
        binding.buttonBackDetailScreen.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun saveLogToShared(id: Int) {
        sharedPrefManager.writeLogData(RECO, id)
//        Toast.makeText(context, "$id Added to log", Toast.LENGTH_SHORT).show()
        Log.v(TAG, "LOG DATA: ${sharedPrefManager.readLogData(RECO)}")
    }

    private fun saveWishlistToShared() {
        if (sharedPrefManager.ifContains(WISHL) == true) {
            wishList = sharedPrefManager.readWishList(WISHL).toMutableList()
        }
        wishList.add(
            WishlistModel(
                id = selectedWishlistModel.id,
                title = selectedWishlistModel.title,
                mediaType = selectedWishlistModel.mediaType,
                voteAverage = selectedWishlistModel.voteAverage,
                backdropPath = selectedWishlistModel.backdropPath,
                genre = selectedWishlistModel.genre
            )
        )
        sharedPrefManager.writeWishList(WISHL, wishList.toTypedArray())
//        Toast.makeText(context, "Add to wishlist", Toast.LENGTH_SHORT).show()
        Log.v(TAG, "added to wishList: $wishList")
    }

    private fun checkIfInWishList(): Boolean {
        if (sharedPrefManager.ifContains(WISHL) == true) {
            wishList = sharedPrefManager.readWishList(WISHL).toMutableList()
        }
        return wishList.contains(
            WishlistModel(
                id = selectedWishlistModel.id,
                title = selectedWishlistModel.title,
                mediaType = selectedWishlistModel.mediaType,
                voteAverage = selectedWishlistModel.voteAverage,
                backdropPath = selectedWishlistModel.backdropPath,
                genre = selectedWishlistModel.genre
            )
        )
    }

    private fun removeWishlist() {
        if (sharedPrefManager.ifContains(WISHL) == true) {
            wishList = sharedPrefManager.readWishList(WISHL).toMutableList()
        }
        wishList.remove(
            WishlistModel(
                id = selectedWishlistModel.id,
                title = selectedWishlistModel.title,
                mediaType = selectedWishlistModel.mediaType,
                voteAverage = selectedWishlistModel.voteAverage,
                backdropPath = selectedWishlistModel.backdropPath,
                genre = selectedWishlistModel.genre
            )
        )
        sharedPrefManager.writeWishList(WISHL, wishList.toTypedArray())
//        Toast.makeText(context, "remove from wishlist", Toast.LENGTH_SHORT).show()
        Log.v(TAG, "removed from wishList: $wishList")
    }

    private fun isSelected(): Boolean {
        if (sharedPrefManager.ifContains(WISHL) == true) {
            wishList = sharedPrefManager.readWishList(WISHL).toMutableList()
        }
        return !wishList.none { it.id == navArgs.id }
    }


    private fun navigateToVideo() {
        binding.buttonTrailer.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToVideoFragment(
                    navArgs.mediaType,
                    navArgs.id
                )
            )
        }
    }

    private fun openSeasonDialog() {
        binding.textViewSeasonNumber.setOnClickListener {
            args.putInt("ID", navArgs.id)
            seasonDialog.arguments = args
            childFragmentManager.let { seasonDialog.show(it, "TAG")
            }
        }
    }

    companion object {
        const val RECO = "RECO"
        const val WISHL = "WISHL"
    }
}
