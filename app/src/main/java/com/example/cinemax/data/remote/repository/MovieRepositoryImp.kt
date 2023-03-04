package com.example.cinemax.data.remote.repository

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.data.entity.search.SearchResponse
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

    override suspend fun getMoviesBySource(source:String,page:Int,genreId : Int?): MoviesResponse {
        return remoteDataSource.getMoviesBySource(source,page,genreId)
    }

    override suspend fun getSearchResult(searchQuery: String, page: Int): SearchResponse {
        return remoteDataSource.getSearchResult(searchQuery,page)
    }


}