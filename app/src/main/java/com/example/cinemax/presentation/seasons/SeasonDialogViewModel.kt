package com.example.cinemax.presentation.seasons

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinemax.data.entity.tvdetail.TvDetailItemResponse
import com.example.cinemax.domain.usecase.tv.TvDetailUseCase
import com.example.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeasonDialogViewModel @Inject constructor (
    private val tvDetailUseCase: TvDetailUseCase,
) : ViewModel() {


    fun getTVDetailResult(id : Int) : LiveData<Resource<TvDetailItemResponse>> {
        return tvDetailUseCase.invoke(id)
    }
}