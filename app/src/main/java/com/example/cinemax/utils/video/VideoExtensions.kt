package com.example.cinemax.utils.video

import androidx.lifecycle.Lifecycle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

fun YouTubePlayer.loadOrCueVideo(lifecycle: Lifecycle, videoId: String, startSeconds: Float) {
    loadOrCueVideo(lifecycle.currentState == Lifecycle.State.RESUMED, videoId, startSeconds)
}


 internal fun YouTubePlayer.loadOrCueVideo(canLoad: Boolean, videoId: String, startSeconds: Float) {
    if (canLoad)
        loadVideo(videoId, startSeconds)
    else
        cueVideo(videoId, startSeconds)
}