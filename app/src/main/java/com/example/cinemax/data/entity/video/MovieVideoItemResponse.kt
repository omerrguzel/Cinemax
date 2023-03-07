package com.example.cinemax.data.entity.video

import com.google.gson.annotations.SerializedName

data class MovieVideoItemResponse(
    @SerializedName("iso_639_1")
    val iso639 : String?,
    @SerializedName("iso_3166_1")
    val iso3166 : String?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("key")
    val key : String,
    @SerializedName("site")
    val site : String?,
    @SerializedName("size")
    val size : String?,
    @SerializedName("type")
    val type : String?,
    @SerializedName("official")
    val official : String?,
    @SerializedName("published_at")
    val publishedAt : String?,
    @SerializedName("id")
    val id : String?
)
