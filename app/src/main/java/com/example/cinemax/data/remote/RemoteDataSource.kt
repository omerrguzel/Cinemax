package com.example.cinemax.data.remote

import androidx.paging.PagingData
import com.example.cinemax.data.entity.movielist.MovieItemResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.utils.BaseDataSource
import com.example.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) :
    BaseDataSource() {

    suspend fun getMovieDetails(id: Int) = getResult { movieApiService.getMovieDetails(id) }

    suspend fun getGenreList() = getResult { movieApiService.getGenreList() }

    suspend fun getMoviesBySource(source: String, query: Int,genreId : Int?) : MoviesResponse =
        movieApiService.getMoviesBySource(source, query, genreId)
}