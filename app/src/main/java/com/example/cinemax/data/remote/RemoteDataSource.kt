package com.example.cinemax.data.remote

import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) :
    BaseDataSource() {

    suspend fun getMovieDetails(id: Int) = getResult { movieApiService.getMovieDetails(id) }

    suspend fun getGenreList(mediaType : String) = getResult { movieApiService.getGenreList(mediaType) }

    suspend fun getMoviesBySource(source: String, page: Int,genreId : Int?) : MoviesResponse =
        movieApiService.getMoviesBySource(source, page, genreId)

    suspend fun getSearchResult(searchQuery:String,page:Int) : SearchResponse =
        movieApiService.getSearchResult(searchQuery,page)
}