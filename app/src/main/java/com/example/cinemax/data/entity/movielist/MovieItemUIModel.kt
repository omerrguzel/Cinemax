package com.example.cinemax.data.entity.movielist


data class MovieItemUIModel(
    val id: Int,
    val overview: String,
    val posterPath: String? = null,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String,
    val backdropPath: String? = null,
    val viewType : Int,
    val genreIds : ArrayList<String>? = arrayListOf()
)
