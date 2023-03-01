package com.example.cinemax.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemax.data.entity.movielist.MovieItemResponse
import com.example.cinemax.data.remote.MovieApiService
import com.example.cinemax.presentation.paging.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val movieApiService: MovieApiService,
    ) : ViewModel() {

    fun getMoviesBySource(sourceName: String): Flow<PagingData<MovieItemResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApiService,sourceName)
            }
        ).flow.cachedIn(viewModelScope)
    }
}