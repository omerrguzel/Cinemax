package com.example.cinemax.data.entity.movielist

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("dates")
    val dates: DateResponse?=null,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieItemResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
