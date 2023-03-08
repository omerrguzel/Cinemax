package com.example.cinemax.presentation.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cinemax.data.entity.video.MovieVideoResponse
import com.example.cinemax.domain.usecase.video.MovieVideoUseCase
import com.example.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val movieVideoUseCase: MovieVideoUseCase
) : ViewModel() {


    fun getMovieVideo(mediaType: String,id: Int): LiveData<Resource<MovieVideoResponse>> {
        return movieVideoUseCase.invoke(mediaType,id)
    }

}
