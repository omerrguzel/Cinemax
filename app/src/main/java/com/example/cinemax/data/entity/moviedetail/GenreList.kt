package com.example.cinemax.data.entity.moviedetail

import com.google.gson.annotations.SerializedName

data class GenreList(
    @SerializedName("genres")
    val genres : List<Genre>
)
