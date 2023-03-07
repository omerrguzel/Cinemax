package com.example.cinemax.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemax.data.entity.search.SearchPersonItemUIModel
import com.example.cinemax.domain.usecase.searchresult.SearchResultUseCase
import javax.inject.Inject

class SearchPersonPagingSource @Inject constructor(
    private val searchResultUseCase: SearchResultUseCase,
    private val searchQuery : String,
) : PagingSource<Int, SearchPersonItemUIModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchPersonItemUIModel> {
        return try {
            val pageNumber = params.key ?: 1
            val response = searchResultUseCase.getSearchResult(searchQuery, pageNumber)
            println("Person Response=$response")

            LoadResult.Page(
                data = response.personList,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = if (pageNumber < response.totalPagesPerson) pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchPersonItemUIModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}