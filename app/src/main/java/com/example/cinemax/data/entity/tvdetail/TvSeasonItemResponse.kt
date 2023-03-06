package com.example.cinemax.data.entity.tvdetail

import com.google.gson.annotations.SerializedName

data class TvSeasonItemResponse(
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("episodes")
    val episodeList: List<TvEpisodeItemResponse> = arrayListOf(),
    @SerializedName("air_date")
    val airDate : String? = null
)
