package com.example.cinemax.data.entity.movielist

data class MoviesUIModel(
    val dates: DateResponse?=null,
    val page: Int,
    val movies: List<MovieItemUIModel>,
    val totalPages: Int,
    val totalResults: Int,
    val viewType : Int
)