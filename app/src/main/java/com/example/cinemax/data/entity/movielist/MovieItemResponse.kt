package com.example.cinemax.data.entity.movielist

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class MovieItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("release_date")
    val releaseDate: String,
)
