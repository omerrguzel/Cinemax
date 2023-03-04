package com.example.cinemax.data.entity.search

import com.example.cinemax.data.entity.movielist.MovieItemResponse
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val searchResults: List<SearchItemResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
