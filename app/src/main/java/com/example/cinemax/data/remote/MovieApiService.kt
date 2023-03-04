package com.example.cinemax.data.remote

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.MovieDetails
import com.example.cinemax.data.entity.movielist.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/{source}?api_key=$API_KEY&language=tr&sort_by=popularity.desc")
    suspend fun getMoviesBySource(
        @Path("source") sourceName: String,
        @Query("page") page: Int,
        @Query("with_genres") genreId : Int?
    ): MoviesResponse


    @GET("movie/{id}?api_key=$API_KEY&language=en-US")
    suspend fun getMovieDetails(@Path("id") id: Int): Response<MovieDetails>

    @GET("genre/movie/list?api_key=$API_KEY&language=en-US")
    suspend fun getGenreList() : Response<GenreListResponse>

    companion object{
        const val API_KEY = "721a8eed90ae677f0f8e7b6b81b314f7"
    }

}