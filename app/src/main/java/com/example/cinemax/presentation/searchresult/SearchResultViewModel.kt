package com.example.cinemax.presentation.searchresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemax.data.entity.search.SearchMediaItemUIModel
import com.example.cinemax.data.entity.search.SearchPersonItemUIModel
import com.example.cinemax.domain.usecase.searchresult.SearchResultUseCase
import com.example.cinemax.presentation.paging.SearchMediaPagingSource
import com.example.cinemax.presentation.paging.SearchPersonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchResultUseCase: SearchResultUseCase
) : ViewModel() {

    fun getSearchMediaResults(searchQuery: String): Flow<PagingData<SearchMediaItemUIModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                SearchMediaPagingSource(searchResultUseCase, searchQuery)
            }
        ).flow.cachedIn(viewModelScope)
    }


    fun getSearchPersonResults(searchQuery: String): Flow<PagingData<SearchPersonItemUIModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                SearchPersonPagingSource(searchResultUseCase, searchQuery)
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun getIfMediaEmpty(searchQuery: String): Boolean {
        var isEmpty: Boolean = false
        SearchMediaPagingSource(searchResultUseCase, searchQuery).isEmptyCheck = {
            if (it) {
                isEmpty = true
            }
        }
        return isEmpty
    }
}