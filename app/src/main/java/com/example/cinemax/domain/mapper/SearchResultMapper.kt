package com.example.cinemax.domain.mapper

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.search.*
import javax.inject.Inject
import kotlin.math.ceil

class SearchResultMapper @Inject constructor() {

    fun mapSearchResponse(
        searchResponse: SearchResponse,
        genreListMovie: GenreListResponse?,
        genreListTV: GenreListResponse?
    ): SearchItemUIModel {
        val newMediaList = mapMediaResponse(
            searchResponse,
            genreListMovie = genreListMovie,
            genreListTV = genreListTV
        )
        return SearchItemUIModel(
            mediaList = newMediaList,
            personList = mapPersonResponse(searchResponse),
            pageMedia = searchResponse.page,
            pagePerson = searchResponse.page,
            totalResultsMedia = newMediaList.size,
            totalResultsPerson = mapPersonResponse(searchResponse).size,
            totalPagesMedia = mapTotalPages(newMediaList.size),
            totalPagesPerson = mapTotalPages(mapPersonResponse(searchResponse).size)
        )
    }

    private fun mapMediaResponse(
        searchResponse: SearchResponse,
        genreListMovie:GenreListResponse?,
        genreListTV: GenreListResponse?
    ): List<SearchMediaItemUIModel> {
        return mapMediaList(
            searchResponse.searchResults.filter {
                (it.mediaType == "movie" || it.mediaType == "tv") && it.posterPath != null && it.genreIds?.isNotEmpty() == true
            },
            genreListMovie = genreListMovie,
            genreListTV = genreListTV
        )
    }


    private fun mapPersonResponse(
        searchResponse: SearchResponse
    ): List<SearchPersonItemUIModel> {
        return mapPersonList(searchResponse.searchResults.filter { it.mediaType == "person" && it.actorImage != null })
    }


    private fun mapMediaList(
        list: List<SearchItemResponse>,
        genreListMovie:GenreListResponse?,
        genreListTV: GenreListResponse?
    ): List<SearchMediaItemUIModel> {
        return list.map {
            SearchMediaItemUIModel(
                mediaType = it.mediaType,
                id = it.id,
                posterPath = it.posterPath,
                movieName = it.movieName,
                voteAverage = it.voteAverage,
                releaseDate = it.releaseDate,
                backdropPath = it.backdropPath,
                genreMovieIds = if(it.mediaType == "movie") mapGenreMovieList(it.genreIds, genreListMovie = genreListMovie) else null,
                genreTvIds = if(it.mediaType == "tv") mapGenreTvList(it.genreIds, genreListTV = genreListTV) else null,
                seriesName = it.seriesName,
                firstAirDate = it.firstAirDate,
            )
        }
    }

    private fun mapPersonList(list: List<SearchItemResponse>): List<SearchPersonItemUIModel> {
        return list.map {
            SearchPersonItemUIModel(
                actorImage = it.actorImage,
                actorName = it.actorName
            )
        }
    }


    private fun mapGenreMovieList(
        idList: List<Int>?,
        genreListMovie: GenreListResponse?,
    ): ArrayList<String> {
        val genreNameList: ArrayList<String> = arrayListOf()

        idList?.forEach { movieGenreId ->
            genreListMovie?.genres?.first {
                movieGenreId == it.id
            }?.name?.let { genreNameList.add(it) }
        }
        return genreNameList
    }

    private fun mapGenreTvList(
        idList: List<Int>?,
        genreListTV: GenreListResponse?,
    ): ArrayList<String> {
        val genreNameList: ArrayList<String> = arrayListOf()

        idList?.forEach { movieGenreId ->
            genreListTV?.genres?.first {
                movieGenreId == it.id
            }?.name?.let { genreNameList.add(it) }
        }
        return genreNameList
    }

    private fun mapTotalPages(num: Int): Int {
        val result = num.toDouble() / 20.0
        return if (result % 1.0 > 0) {
            ceil(result).toInt()
        } else {
            result.toInt()
        }
    }
}