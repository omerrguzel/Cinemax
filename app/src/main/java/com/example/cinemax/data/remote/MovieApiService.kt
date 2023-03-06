package com.example.cinemax.data.remote

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.MovieDetailItemResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.data.entity.tvdetail.TvDetailItemResponse
import com.example.cinemax.data.entity.tvdetail.TvSeasonItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/{source}?api_key=$API_KEY&language=en-US&sort_by=popularity.desc")
    suspend fun getMoviesBySource(
        @Path("source") sourceName: String,
        @Query("page") page: Int,
        @Query("with_genres") genreId : Int?
    ): MoviesResponse

    @GET("search/multi?api_key=$API_KEY&language=en-US&sort_by=vote_count.desc")
        suspend fun getSearchResult(
        @Query("query") searchQuery : String,
        @Query("page") page : Int
    ) : SearchResponse

    @GET("movie/{id}?api_key=$API_KEY&language=en-US")
    suspend fun getMovieDetails(@Path("id") id: Int): Response<MovieDetailItemResponse>

    @GET("tv/{id}?api_key=$API_KEY&language=en-US")
    suspend fun getTvDetails(@Path("id") id: Int): Response<TvDetailItemResponse>

    @GET("tv/{id}/season/{seasonNumber}?api_key=$API_KEY&language=en-US")
    suspend fun getSeasonDetails(@Path("id") id: Int,@Path("seasonNumber") seasonNumber : Int): Response<TvSeasonItemResponse>


    @GET("genre/{mediaType}/list?api_key=$API_KEY&language=en-US")
    suspend fun getGenreList(
        @Path("mediaType") mediaType : String
    ) : Response<GenreListResponse>

    companion object{
        const val API_KEY = "721a8eed90ae677f0f8e7b6b81b314f7"
    }

}