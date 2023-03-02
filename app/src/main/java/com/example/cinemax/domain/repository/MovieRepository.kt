package com.example.cinemax.domain.repository

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.utils.Resource

interface MovieRepository {

    suspend fun getGenreList() : Resource<GenreListResponse>

    suspend fun getMoviesBySource(source:String,query:Int) : MoviesResponse
}