package com.example.cinemax.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemax.data.entity.search.SearchMediaItemUIModel
import com.example.cinemax.domain.usecase.search.SearchUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMediaPagingSource @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val searchQuery : String
) : PagingSource<Int, SearchMediaItemUIModel>() {

    var isEmptyCheck : (( isMediaEmpty : Boolean) -> Unit)? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchMediaItemUIModel> {


        try {
            // Start refresh at page 1 if undefined.
            val pageNumber  = params.key ?: 1
            val response = searchUseCase.getSearchResult(searchQuery, pageNumber )
            return LoadResult.Page(
                data = response.mediaList,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = if (pageNumber < response.totalPagesMedia) pageNumber + 1 else null
            ).also {
                if(response.totalResultsMedia == 0){
                    isEmptyCheck?.invoke(true)
                }
            }
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchMediaItemUIModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
