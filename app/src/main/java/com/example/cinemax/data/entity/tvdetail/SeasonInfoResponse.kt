package com.example.cinemax.data.entity.tvdetail

import com.google.gson.annotations.SerializedName

data class SeasonInfoResponse(
    @SerializedName("season_number")
    val seasonNumber : Int,
    @SerializedName("name")
    val name : String,
)
