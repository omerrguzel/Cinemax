package com.example.cinemax.data.entity.moviedetail

import com.google.gson.annotations.SerializedName

data class MovieDetailItemResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_title")
    val movieName: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate : String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("vote_average")
    val voteRating: Double,
    @SerializedName("genres")
    val genres: List<GenreResponse>,
    @SerializedName("overview")
    val overview: String
)