package com.example.cinemax.domain.repository

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.utils.Resource

interface MovieRepository {

    suspend fun getGenreList(mediaType : String) : Resource<GenreListResponse>

    suspend fun getMoviesBySource(source:String,page:Int,genreId : Int?) : MoviesResponse

    suspend fun getSearchResult(searchQuery: String,page : Int) : SearchResponse
}