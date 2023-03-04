package com.example.cinemax.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.movielist.MovieItemUIModel
import com.example.cinemax.domain.usecase.movie.GenreUseCase
import com.example.cinemax.domain.usecase.movie.MovieListUseCase
import com.example.cinemax.presentation.paging.MoviePagingSource
import com.example.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val movieListUseCase: MovieListUseCase,
    private val genreUseCase: GenreUseCase
    ) : ViewModel() {

    fun getMoviesBySource(sourceName: String,viewType:Int,genreId : Int?): Flow<PagingData<MovieItemUIModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieListUseCase,sourceName,viewType,genreId)
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getGenres() : LiveData<Resource<GenreListResponse>>{
        return genreUseCase.invoke()
    }
}