package com.example.cinemax.utils.video

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

interface YouTubePlayerListener {

    fun onReady(youTubePlayer: YouTubePlayer)

    fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState)

    fun onPlaybackQualityChange(youTubePlayer: YouTubePlayer, playbackQuality: PlayerConstants.PlaybackQuality)

    fun onPlaybackRateChange(youTubePlayer: YouTubePlayer, playbackRate: PlayerConstants.PlaybackRate)

    fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError)

    fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float)

    fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float)

    fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float)

    fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String)

    fun onApiChange(youTubePlayer: YouTubePlayer)
}