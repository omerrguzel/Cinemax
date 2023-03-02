package com.example.cinemax.domain.repository

import com.example.cinemax.data.entity.moviedetail.GenreList
import com.example.cinemax.utils.Resource

interface MovieRepository {

    suspend fun getGenreList() : Resource<GenreList>
}