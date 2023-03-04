package com.example.cinemax.data.entity.search


data class SearchMediaItemUIModel(
    val mediaType: String,
    val id: Int,
    val posterPath: String?,
    val movieName: String?,
    val voteAverage: Double?,
    val releaseDate: String?,
    val backdropPath: String?,
    val genreMovieIds : ArrayList<String>?,
    val genreTvIds : ArrayList<String>?,
    val seriesName: String?,
    val firstAirDate: String?
)
