package com.example.cinemax.data.entity.moviedetail

data class MovieDetails(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Double,
    val release_date: String,
    val runtime: Int,
    val genres: List<Genre>,
)