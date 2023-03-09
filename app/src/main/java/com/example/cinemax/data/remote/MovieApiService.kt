package com.example.cinemax.data.remote

import com.example.cinemax.BuildConfig
import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.MovieDetailItemResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.data.entity.tvdetail.TvDetailItemResponse
import com.example.cinemax.data.entity.tvdetail.TvSeasonItemResponse
import com.example.cinemax.data.entity.video.MovieVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/{source}?api_key=$API_KEY&sort_by=popularity.desc")
    suspend fun getMoviesBySource(
        @Path("source") sourceName: String,
        @Query("page") page: Int?,
        @Query("with_genres") genreId : Int?,
        @Query("language") language: String
    ): MoviesResponse

    @GET("movie/{movieId}/recommendations?api_key=$API_KEY&sort_by=popularity.desc")
    suspend fun getRecommendation(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int?,
        @Query("language") language: String
    ): MoviesResponse

    @GET("movie/now_playing?api_key=$API_KEY&sort_by=popularity.desc")
    suspend fun getNowPlayingItem(
        @Query("language") language: String
    ): Response<MoviesResponse>

    @GET("search/multi?api_key=$API_KEY&sort_by=vote_count.desc")
        suspend fun getSearchResult(
        @Query("query") searchQuery : String,
        @Query("page") page : Int,
        @Query("language") language: String
    ) : SearchResponse

    @GET("movie/{id}?api_key=$API_KEY")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("language") language: String
    ): Response<MovieDetailItemResponse>

    @GET("tv/{id}?api_key=$API_KEY")
    suspend fun getTvDetails(
        @Path("id") id: Int,
        @Query("language") language: String
    ): Response<TvDetailItemResponse>

    @GET("tv/{id}/season/{seasonNumber}?api_key=$API_KEY")
    suspend fun getSeasonDetails(
        @Path("id") id: Int,
        @Path("seasonNumber") seasonNumber : Int,
        @Query("language") language: String
    ): Response<TvSeasonItemResponse>


    @GET("genre/{mediaType}/list?api_key=$API_KEY")
    suspend fun getGenreList(
        @Path("mediaType") mediaType : String,
        @Query("language") language: String
    ) : Response<GenreListResponse>

    @GET("{mediaType}/{movieId}/videos?api_key=$API_KEY")
    suspend fun getMovieVideo(
        @Path("mediaType") mediaType: String,
        @Path("movieId") movieId : Int,
        @Query("language") language: String
    ) : Response<MovieVideoResponse>


    companion object{
        const val API_KEY = BuildConfig.API_KEY
    }

}