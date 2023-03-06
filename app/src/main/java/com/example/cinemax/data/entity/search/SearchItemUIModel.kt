package com.example.cinemax.data.entity.search

data class SearchItemUIModel(
    val mediaList: List<SearchMediaItemUIModel>,
    val personList: List<SearchPersonItemUIModel>,
    val totalPagesPerson: Int,
    val totalResultsPerson: Int,
    val pagePerson: Int,
    val totalPagesMedia: Int,
    val totalResultsMedia: Int,
    val pageMedia: Int
)
