package com.example.cinemax.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemax.data.entity.movielist.MovieItemResponse
import com.example.cinemax.data.remote.MovieApiService
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieApiService: MovieApiService,
    private val sourceName: String
) : PagingSource<Int, MovieItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemResponse> {
        return try {
            val pageNumber = params.key ?: 1
            val response = movieApiService.getMoviesBySource(sourceName, pageNumber)
            println(response)

            LoadResult.Page(
                data = response.movies,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = if (pageNumber < response.totalPages) pageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}