package com.example.cinemax.data.entity.video

import com.google.gson.annotations.SerializedName

data class MovieVideoItemResponse(
    @SerializedName("name")
    val name : String?,
    @SerializedName("key")
    val key : String,
    @SerializedName("id")
    val id : String?
)
