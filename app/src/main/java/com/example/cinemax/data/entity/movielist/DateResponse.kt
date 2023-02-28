package com.example.cinemax.data.entity.movielist

import com.google.gson.annotations.SerializedName

data class DateResponse(
    @SerializedName("maximum")
    val firstDate: String,
    @SerializedName("minimum")
    val lastDate: String
)
