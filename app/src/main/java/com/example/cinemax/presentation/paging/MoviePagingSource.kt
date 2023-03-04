package com.example.cinemax.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemax.data.entity.movielist.MovieItemUIModel
import com.example.cinemax.domain.usecase.movie.MovieListUseCase
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val sourceName: String,
    private val viewType : Int,
    private val genreId : Int?
) : PagingSource<Int, MovieItemUIModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemUIModel> {
        return try {
            val pageNumber = params.key ?: 1
            val loadSize = params.loadSize
            val response = movieListUseCase.getMoviesBySource(sourceName, pageNumber,viewType,genreId)
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