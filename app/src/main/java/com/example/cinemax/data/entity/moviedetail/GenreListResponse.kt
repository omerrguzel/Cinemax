package com.example.cinemax.data.entity.moviedetail

import com.google.gson.annotations.SerializedName

data class GenreListResponse(
    @SerializedName("genres")
    val genres : List<GenreResponse>
)
