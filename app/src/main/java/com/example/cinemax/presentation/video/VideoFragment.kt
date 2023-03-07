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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cinemax.databinding.FragmentVideoBinding
import com.example.cinemax.utils.Resource
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.show
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment() {

    private lateinit var binding : FragmentVideoBinding
    private val viewModel: VideoViewModel by viewModels()
    private val navArgs: VideoFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieVideo(navArgs.id)
        backButtonController()
    }

    private fun initVideo(videoKey : String?){
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (videoKey != null) {
                    youTubePlayer.loadVideo(videoKey, 0f)
                }
            }
        })
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.youtubePlayerView.enterFullScreen()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.youtubePlayerView.exitFullScreen()
        }
    }

    private fun getMovieVideo(id: Int) {
        viewModel.getMovieVideo(id).observe(viewLifecycleOwner) {
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
                    initVideo(it.data?.results?.get(0)?.key)
                }
            }
        }
    }



    private fun backButtonController() {
        binding.buttonBackVideoScreen.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}