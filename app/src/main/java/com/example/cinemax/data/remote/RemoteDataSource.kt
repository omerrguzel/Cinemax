package com.example.cinemax.data.remote

import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.utils.BaseDataSource
import java.util.*
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieApiService: MovieApiService) :
    BaseDataSource() {

    suspend fun getGenreList(mediaType : String) = getResult { movieApiService.getGenreList(mediaType,language = Locale.getDefault().language) }

    suspend fun getNowPlayingItem() = getResult { movieApiService.getNowPlayingItem(language = Locale.getDefault().language) }
    suspend fun getMoviesBySource(source: String, page: Int,genreId : Int?) : MoviesResponse =
        movieApiService.getMoviesBySource(source, page, genreId,language = Locale.getDefault().language)

    suspend fun getRecommendation(movieId : Int,page: Int) : MoviesResponse =
        movieApiService.getRecommendation(movieId,page,language = Locale.getDefault().language)

    suspend fun getSearchResult(searchQuery:String,page:Int) : SearchResponse =
        movieApiService.getSearchResult(searchQuery,page,language = Locale.getDefault().language)

    suspend fun getTVDetailResult(id:Int) = getResult { movieApiService.getTvDetails(id,language = Locale.getDefault().language) }

    suspend fun getSeasonDetails(id:Int,seasonNumber : Int) = getResult { movieApiService.getSeasonDetails(id,seasonNumber,language = Locale.getDefault().language) }

    suspend fun getMovieDetailResult(id: Int) = getResult { movieApiService.getMovieDetails(id,language = Locale.getDefault().language) }

    suspend fun getMovieVideo(mediaType: String,id: Int,) = getResult { movieApiService.getMovieVideo(mediaType,id,language = Locale.getDefault().language) }


}