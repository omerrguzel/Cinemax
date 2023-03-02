package com.example.cinemax.data.remote.repository

import com.example.cinemax.data.entity.moviedetail.Genre
import com.example.cinemax.data.entity.moviedetail.GenreList
import com.example.cinemax.data.remote.RemoteDataSource
import com.example.cinemax.domain.repository.MovieRepository
import com.example.cinemax.utils.Resource
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {
    override suspend fun getGenreList(): Resource<GenreList> {
        return remoteDataSource.getGenreList()
    }


}