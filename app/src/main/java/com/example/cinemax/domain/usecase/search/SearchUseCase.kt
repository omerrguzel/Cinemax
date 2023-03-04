package com.example.cinemax.domain.usecase.search

import com.example.cinemax.data.entity.search.SearchItemUIModel
import com.example.cinemax.domain.mapper.SearchResultMapper
import com.example.cinemax.domain.repository.MovieRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getSearchResult(
        searchQuery: String,
        page: Int
    ): SearchItemUIModel {
        val searchResponse = movieRepository.getSearchResult(searchQuery, page)
        val genreMovieResponse = movieRepository.getGenreList("movie").data
        val genreTVResponse = movieRepository.getGenreList("tv").data
        println("Service Response=$genreTVResponse")
        println("Service Response=$genreMovieResponse")
        return SearchResultMapper().mapSearchResponse(searchResponse, genreListMovie = genreMovieResponse, genreListTV = genreTVResponse)
    }
}