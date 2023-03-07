package com.example.cinemax.data.entity.tvdetail

import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.google.gson.annotations.SerializedName

data class TvDetailItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_name")
    val tvName: String,
    @SerializedName("episode_run_time")
    val runTime: List<Int> = listOf(),
    @SerializedName("genres")
    val genres: List<GenreResponse>,
    @SerializedName("seasons")
    val seasonInfoList: List<SeasonInfoResponse>,
    @SerializedName("vote_average")
    val voteRating: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("backdrop_path")
    val backdropPath: String?
)
