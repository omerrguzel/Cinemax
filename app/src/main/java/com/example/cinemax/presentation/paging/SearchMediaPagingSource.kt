package com.example.cinemax.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemax.data.entity.search.SearchMediaItemUIModel
import com.example.cinemax.domain.usecase.search.SearchUseCase
import javax.inject.Inject

class SearchMediaPagingSource @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val searchQuery : String
) : PagingSource<Int, SearchMediaItemUIModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchMediaItemUIModel> {
        return try {
            val pageNumber = params.key ?: 1
            val response = searchUseCase.getSearchResult(searchQuery, pageNumber)
            println("Media Response=${response.mediaList}")

            LoadResult.Page(
                data = response.mediaList,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = if (pageNumber < response.totalPages) pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchMediaItemUIModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
