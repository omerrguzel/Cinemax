package com.example.cinemax.data.entity.search

data class SearchItemUIModel(
    val mediaList: List<SearchMediaItemUIModel>,
    val personList: List<SearchPersonItemUIModel>,
    val totalPages: Int,
    val totalResults: Int,
    val page: Int
)
