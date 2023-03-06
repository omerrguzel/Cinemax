package com.example.cinemax.data.entity.tvdetail

import com.google.gson.annotations.SerializedName

data class TvEpisodeItemResponse(
    @SerializedName("episode_number")
    val episodeNumber: Int?,
    @SerializedName("name")
    val episodeName: String?,
    @SerializedName("runtime")
    val runtime : Int?,
    @SerializedName("still_path")
    val stillPath : String?,
    @SerializedName("crew")
    val crewList : List<CrewItem>? = arrayListOf(),
    @SerializedName("overview")
    val overviewEpisode : String?
)
