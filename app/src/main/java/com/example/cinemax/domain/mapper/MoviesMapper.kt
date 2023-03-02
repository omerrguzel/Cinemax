package com.example.cinemax.domain.mapper

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.movielist.*
import javax.inject.Inject

class MoviesMapper @Inject constructor() {
    fun mapResponse(
        moviesResponse: MoviesResponse,
        viewType: Int,
        genreList: GenreListResponse?
    ): MoviesUIModel {
        return MoviesUIModel(
            dates = moviesResponse.dates,
            page = moviesResponse.page,
            movies = mapItemResponse(moviesResponse.movies, viewType, genreList),
            totalPages = moviesResponse.totalPages,
            totalResults = moviesResponse.totalResults,
            viewType = viewType
        )
    }

    private fun mapItemResponse(
        listMoviesItemResponse: List<MovieItemResponse>,
        viewType: Int,
        genreList: GenreListResponse?
    ): List<MovieItemUIModel> {
        return listMoviesItemResponse.map {
            MovieItemUIModel(
                id = it.id,
                overview = it.overview,
                posterPath = it.posterPath,
                title = it.title,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                backdropPath = it.backdropPath,
                viewType = viewType,
                genreIds = mapGenreList(it.genreIds, genreList)
            )
        }
    }

    private fun mapGenreList(idList: List<Int>, genreList: GenreListResponse?): ArrayList<String> {
        val genreNameList: ArrayList<String> = arrayListOf()

        idList.forEach { movieGenreId ->
            genreList?.genres?.first {
                movieGenreId== it.id
            }?.name?.let { genreNameList.add(it) }

        }
        return genreNameList
    }
}