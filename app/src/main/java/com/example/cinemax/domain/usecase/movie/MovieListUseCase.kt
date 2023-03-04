package com.example.cinemax.domain.usecase.movie

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.movielist.MoviesUIModel
import com.example.cinemax.domain.mapper.MoviesMapper
import com.example.cinemax.domain.repository.MovieRepository

import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMoviesBySource(
        source: String,
        query: Int,
        viewType: Int,
        genreId : Int?
    ): MoviesUIModel {
        val moviesResponse = movieRepository.getMoviesBySource(source, query,genreId)
        val genreResponse = movieRepository.getGenreList().data
        return MoviesMapper().mapResponse(moviesResponse, viewType, genreResponse)
    }
}
