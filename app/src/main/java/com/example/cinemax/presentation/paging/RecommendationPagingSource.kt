package com.example.cinemax.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemax.data.entity.movielist.MovieItemUIModel
import com.example.cinemax.domain.usecase.movie.MovieListUseCase
import com.example.cinemax.domain.usecase.search.RecommendationUseCase
import javax.inject.Inject

class RecommendationPagingSource @Inject constructor(
    private val recommendationUseCase: RecommendationUseCase,
    private val movieId: Int,
    private val viewType : Int,
) : PagingSource<Int, MovieItemUIModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemUIModel> {
        return try {
            val pageNumber = params.key ?: 1
            val response = recommendationUseCase.getRecommendations(movieId, pageNumber,viewType)
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

    override fun getRefreshKey(state: PagingState<Int, MovieItemUIModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}