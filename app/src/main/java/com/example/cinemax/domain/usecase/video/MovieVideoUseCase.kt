package com.example.cinemax.domain.usecase.video

import com.example.cinemax.domain.repository.MovieRepository
import com.example.cinemax.utils.performNetworkOperation
import javax.inject.Inject


class MovieVideoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(mediaType: String,id:Int) = performNetworkOperation {
        movieRepository.getMovieVideo(mediaType,id)
    }
}