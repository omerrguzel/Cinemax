package com.example.cinemax.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.movielist.MovieItemUIModel
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.domain.usecase.movie.MovieListUseCase
import com.example.cinemax.domain.usecase.search.RecommendationUseCase
import com.example.cinemax.domain.usecase.search.SearchUseCase
import com.example.cinemax.presentation.paging.MoviePagingSource
import com.example.cinemax.presentation.paging.RecommendationPagingSource
import com.example.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val recommendationUseCase: RecommendationUseCase
    ) : ViewModel() {

    fun getNowPlayingItem() : LiveData<Resource<MoviesResponse>> {
        return searchUseCase.invoke()
    }

    fun getRecommendations(movieId: Int,viewType:Int): Flow<PagingData<MovieItemUIModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                RecommendationPagingSource(recommendationUseCase,movieId,viewType)
            }
        ).flow.cachedIn(viewModelScope)
    }
}