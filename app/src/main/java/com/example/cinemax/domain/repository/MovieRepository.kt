package com.example.cinemax.domain.repository

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.moviedetail.MovieDetailItemResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.data.entity.tvdetail.TvDetailItemResponse
import com.example.cinemax.data.entity.tvdetail.TvSeasonItemResponse
import com.example.cinemax.data.entity.video.MovieVideoResponse
import com.example.cinemax.utils.Resource

interface MovieRepository {

    suspend fun getGenreList(mediaType : String) : Resource<GenreListResponse>

    suspend fun getNowPlayingItem() : Resource<MoviesResponse>

    suspend fun getMoviesBySource(source:String,page:Int,genreId : Int?) : MoviesResponse

    suspend fun getRecommendations(movieId : Int,page : Int) : MoviesResponse

    suspend fun getSearchResult(searchQuery: String,page : Int) : SearchResponse

    suspend fun getTVDetailResult(id : Int) : Resource<TvDetailItemResponse>

    suspend fun getSeasonDetails(id: Int,seasonNumber : Int) : Resource<TvSeasonItemResponse>

    suspend fun getMovieDetailResult(id: Int) : Resource<MovieDetailItemResponse>

    suspend fun getMovieVideo(mediaType: String,id: Int) : Resource<MovieVideoResponse>

}