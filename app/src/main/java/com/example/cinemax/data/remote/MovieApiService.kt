package com.example.cinemax.data.remote

import com.example.cinemax.data.entity.movielist.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("{source}?api_key=$API_KEY&language=en-US")
    suspend fun getMoviesBySource(
        @Path("source") sourceName: String,
        @Query("page") page: Int
    ): MoviesResponse

    companion object{
        const val API_KEY = "721a8eed90ae677f0f8e7b6b81b314f7"
    }

}