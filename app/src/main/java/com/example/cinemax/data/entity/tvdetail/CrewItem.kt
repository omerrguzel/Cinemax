package com.example.cinemax.data.entity.tvdetail

import com.google.gson.annotations.SerializedName

data class CrewItem(
    @SerializedName("job")
    val crewJob: String?,
    @SerializedName("original_name")
    val crewName: String?,
    @SerializedName("profile_path")
    val crewProfilePath : String?,
)
