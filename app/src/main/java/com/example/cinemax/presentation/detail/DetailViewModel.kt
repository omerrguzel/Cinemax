package com.example.cinemax.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinemax.data.entity.moviedetail.MovieDetailItemResponse
import com.example.cinemax.data.entity.tvdetail.TvDetailItemResponse
import com.example.cinemax.data.entity.tvdetail.TvSeasonItemResponse
import com.example.cinemax.domain.usecase.MovieDetailUseCase
import com.example.cinemax.domain.usecase.tv.TvDetailUseCase
import com.example.cinemax.domain.usecase.tv.TvSeasonUseCase
import com.example.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (
    private val tvDetailUseCase: TvDetailUseCase,
    private val tvSeasonUseCase: TvSeasonUseCase,
    private val movieDetailUseCase: MovieDetailUseCase
) : ViewModel() {


    fun getTVDetailResult(id : Int) : LiveData<Resource<TvDetailItemResponse>> {
        return tvDetailUseCase.invoke(id)
    }

    fun getSeasonDetails(id : Int,seasonNumber : Int) : LiveData<Resource<TvSeasonItemResponse>> {
        return tvSeasonUseCase.invoke(id,seasonNumber)
    }

    fun getMovieDetails(id : Int) : LiveData<Resource<MovieDetailItemResponse>> {
        return movieDetailUseCase.invoke(id)
    }
}