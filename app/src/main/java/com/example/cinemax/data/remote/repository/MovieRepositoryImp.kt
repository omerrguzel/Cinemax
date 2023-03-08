package com.example.cinemax.data.remote.repository

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.moviedetail.MovieDetailItemResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
import com.example.cinemax.data.entity.tvdetail.TvDetailItemResponse
import com.example.cinemax.data.entity.tvdetail.TvSeasonItemResponse
import com.example.cinemax.data.entity.video.MovieVideoResponse
import com.example.cinemax.data.remote.RemoteDataSource
import com.example.cinemax.domain.repository.MovieRepository
import com.example.cinemax.utils.Resource
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override suspend fun getGenreList(mediaType: String): Resource<GenreListResponse> {
        return remoteDataSource.getGenreList(mediaType)
    }

    override suspend fun getNowPlayingItem(): Resource<MoviesResponse> {
        return remoteDataSource.getNowPlayingItem()
    }

    override suspend fun getMoviesBySource(source:String,page:Int,genreId : Int?): MoviesResponse {
        return remoteDataSource.getMoviesBySource(source,page,genreId)
    }

    override suspend fun getRecommendations(movieId: Int, page: Int): MoviesResponse {
        return remoteDataSource.getRecommendation(movieId,page)
    }

    override suspend fun getSearchResult(searchQuery: String, page: Int): SearchResponse {
        return remoteDataSource.getSearchResult(searchQuery,page)
    }

    override suspend fun getTVDetailResult(id : Int) : Resource<TvDetailItemResponse> {
        return remoteDataSource.getTVDetailResult(id)
    }

    override suspend fun getSeasonDetails(id: Int,seasonNumber : Int) : Resource<TvSeasonItemResponse> {
        return remoteDataSource.getSeasonDetails(id,seasonNumber)
    }

    override suspend fun getMovieDetailResult(id: Int) : Resource<MovieDetailItemResponse> {
        return remoteDataSource.getMovieDetailResult(id)
    }

    override suspend fun getMovieVideo(mediaType: String,id: Int): Resource<MovieVideoResponse> {
        return remoteDataSource.getMovieVideo(mediaType,id)
    }

}