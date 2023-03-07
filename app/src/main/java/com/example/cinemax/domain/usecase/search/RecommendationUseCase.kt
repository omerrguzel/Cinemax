package com.example.cinemax.domain.usecase.search

import com.example.cinemax.data.entity.movielist.MoviesUIModel
import com.example.cinemax.domain.mapper.MoviesMapper
import com.example.cinemax.domain.repository.MovieRepository
import javax.inject.Inject


class RecommendationUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getRecommendations(
        movieId: Int,
        page: Int,
        viewType: Int
    ): MoviesUIModel {
        val moviesResponse = movieRepository.getRecommendations(movieId, page)
        val genreResponse = movieRepository.getGenreList("movie").data
        return MoviesMapper().mapResponse(moviesResponse, viewType, genreResponse)
    }
}