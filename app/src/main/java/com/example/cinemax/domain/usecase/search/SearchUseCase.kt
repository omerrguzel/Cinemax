package com.example.cinemax.domain.usecase.search

import com.example.cinemax.domain.repository.MovieRepository
import com.example.cinemax.utils.performNetworkOperation
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = performNetworkOperation {
        movieRepository.getNowPlayingItem()
    }
}