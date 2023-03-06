package com.example.cinemax.presentation.detail

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cinemax.databinding.FragmentDetailBinding
import com.example.cinemax.presentation.adapter.CrewAdapter
import com.example.cinemax.presentation.adapter.EpisodeAdapter
import com.example.cinemax.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val navArgs: DetailFragmentArgs by navArgs()
    private var crewAdapter: CrewAdapter = CrewAdapter(arrayListOf())
    private var episodeAdapter: EpisodeAdapter = EpisodeAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButtonController()
        initDetailMediaType()
    }

    private fun initDetailMediaType(){
        if (navArgs.mediaType == "tv") {
            getTVDetailResult(navArgs.id)
            getSeasonDetails(navArgs.id, 1)
        } else {
            getMovieDetails(navArgs.id)
        }
    }

    private fun getTVDetailResult(id: Int) {
        viewModel.getTVDetailResult(id).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(ContentValues.TAG, "Fetch Info Error: ${it.message}")
                }
                Resource.Status.SUCCESS -> {
                    Log.d(ContentValues.TAG, "TvDetailResult: ${it.data}")
                    binding.progressBar.gone()
                    binding.apply {
                        textViewTitleDetail.text = it.data?.tvName
                        textViewOverview.text = it.data?.overview
                        textViewGenreDetail.text = it.data?.genres?.get(0)?.name
                        textViewRatingDetail.text = it.data?.voteRating?.roundRating()
                        textViewSeasonNumber.text = it.data?.seasonInfoList?.get(0)?.name
                        imageViewBackgroundDetailPoster.showImage(it.data?.posterPath ?: "null")
                        imageViewForegroundDetailPoster.showImage(it.data?.posterPath ?: "null")
                        if (it.data?.runTime?.isEmpty() == true) {
                            textViewDuration.text = ""
                        } else textViewDuration.text = it.data?.runTime?.get(0).toString()
                    }
                }
            }
        }
    }

    private fun getSeasonDetails(id: Int, seasonNumber: Int) {
        viewModel.getSeasonDetails(id, seasonNumber).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(ContentValues.TAG, "Fetch Info Error: ${it.message}")
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
                        if (it.data?.episodeList?.get(0)?.crewList?.isEmpty() == true) {
                            recyclerViewCastAndCrew.gone()
                            textViewCastTitle.gone()
                        }
                    }
                }
            }
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
                    Log.d(ContentValues.TAG, "TvDetailResult: ${it.data}")
                    binding.progressBar.gone()
                    binding.apply {
                        textViewTitleDetail.text = it.data?.movieName
                        textViewReleaseDateDetail.text = it.data?.releaseDate?.showOnlyYear()
                        textViewDuration.text = it.data?.runtime?.toString()
                        textViewGenreDetail.text = it.data?.genres?.get(0)?.name.toString()
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
    private fun backButtonController() {
        binding.buttonBackDetailScreen.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
