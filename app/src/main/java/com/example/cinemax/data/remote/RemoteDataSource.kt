package com.example.cinemax.data.remote

import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) :
    BaseDataSource() {

    suspend fun getGenreList(mediaType : String) = getResult { movieApiService.getGenreList(mediaType) }

    suspend fun getNowPlayingItem() = getResult { movieApiService.getNowPlayingItem() }
    suspend fun getMoviesBySource(source: String, page: Int,genreId : Int?) : MoviesResponse =
        movieApiService.getMoviesBySource(source, page, genreId)

    suspend fun getRecommendation(movieId : Int,page: Int) : MoviesResponse =
        movieApiService.getRecommendation(movieId,page)

    suspend fun getSearchResult(searchQuery:String,page:Int) : SearchResponse =
        movieApiService.getSearchResult(searchQuery,page)

    suspend fun getTVDetailResult(id:Int) = getResult { movieApiService.getTvDetails(id) }

    suspend fun getSeasonDetails(id:Int,seasonNumber : Int) = getResult { movieApiService.getSeasonDetails(id,seasonNumber) }

    suspend fun getMovieDetailResult(id: Int) = getResult { movieApiService.getMovieDetails(id) }

    suspend fun getMovieVideo(id: Int) = getResult { movieApiService.getMovieVideo(id) }


}