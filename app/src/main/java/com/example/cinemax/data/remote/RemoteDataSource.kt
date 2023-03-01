package com.example.cinemax.data.remote

import com.example.cinemax.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) :
    BaseDataSource() {

    suspend fun getMovieDetails(id: Int) = getResult { movieApiService.getMovieDetails(id) }

}