package com.example.cinemax.data.entity.movielist


data class MovieItemUIModel(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String,
    val backdropPath: String,
    val viewType : Int,
    val genreIds : ArrayList<String>?
)
