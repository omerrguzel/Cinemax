package com.example.cinemax.domain.usecase.tv

import com.example.cinemax.domain.repository.MovieRepository
import com.example.cinemax.utils.performNetworkOperation
import javax.inject.Inject

class TvDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(id:Int) = performNetworkOperation {
        movieRepository.getTVDetailResult(id)
    }
}