package com.example.cinemax.data.entity.search

import com.google.gson.annotations.SerializedName

data class SearchItemResponse(
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val movieName: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds : List<Int>? = null,
    @SerializedName("original_name")
    val actorName: String?,
    @SerializedName("profile_path")
    val actorImage: String?,
    @SerializedName("name")
    val seriesName: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?
)
