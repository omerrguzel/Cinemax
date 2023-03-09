package com.example.cinemax.presentation.video

import android.content.ContentValues
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cinemax.R
import com.example.cinemax.databinding.FragmentVideoBinding
import com.example.cinemax.presentation.adapter.VideoListAdapter
import com.example.cinemax.utils.Resource
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.setMargins
import com.example.cinemax.utils.show
import com.example.cinemax.utils.video.FullScreenHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding
    private val viewModel: VideoViewModel by viewModels()
    private val navArgs: VideoFragmentArgs by navArgs()
    private val videoAdapter: VideoListAdapter = VideoListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getMovieVideo(navArgs.mediaType,navArgs.id)
        backButtonController()
    }



    private fun initVideo(videoKey: String?) {
        val youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        val listener = object:AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {

                val defaultPlayerUiController =
                    DefaultPlayerUiController(youTubePlayerView, youTubePlayer)
                youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)

                if (lifecycle.currentState == Lifecycle.State.RESUMED)
                    videoKey?.let { youTubePlayer.loadVideo(it, 0f) }

                setTitleSelectorListener(youTubePlayer)

            }
        }
        val options: IFramePlayerOptions  =  IFramePlayerOptions.Builder().controls(0).build()

        youTubePlayerView.initialize(listener, options)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val fullScreenHelper = FullScreenHelper(this,
            binding.recyclerViewVideoList,
            binding.buttonBackVideoScreen
        )

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fullScreenHelper.enterFullScreen()
            binding.youtubePlayerView.enterFullScreen()
            binding.linearLayoutVideo.setMargins(top=0)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            fullScreenHelper.exitFullScreen()
            binding.youtubePlayerView.exitFullScreen()
            binding.linearLayoutVideo.setMargins(top = resources.getDimensionPixelSize(R.dimen._100sdp))
        }
    }

    private fun getMovieVideo(mediaType: String,id: Int) {
        viewModel.getMovieVideo(mediaType,id).observe(viewLifecycleOwner) {
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
                    val reversedVideoList = it.data?.results?.asReversed()
                    videoAdapter.setData(reversedVideoList)
                    binding.recyclerViewVideoList.adapter = videoAdapter

                    if (!reversedVideoList.isNullOrEmpty()) initVideo(reversedVideoList[0].key)
                }
            }
        }
    }

    private fun setTitleSelectorListener(
        youTubePlayer: YouTubePlayer,
    ) {
        videoAdapter.videoSelectListener = { it ->
            if (lifecycle.currentState == Lifecycle.State.RESUMED)
                it?.let { youTubePlayer.loadVideo(it, 0f) }
        }
    }


    private fun backButtonController() {
        binding.buttonBackVideoScreen.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}