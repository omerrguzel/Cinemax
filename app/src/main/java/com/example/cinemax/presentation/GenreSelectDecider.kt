package com.example.cinemax.presentation

import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import javax.inject.Inject

class GenreSelectDecider @Inject constructor() {
    internal sealed class Genre(open val id : Int){
        data class Unselected(override val id : Int) : Genre(id)
        data class Selected(override val id : Int) : Genre(id)
    }

    private val genreSet = mutableSetOf<Genre>()

    private lateinit var unselected: Genre.Unselected

    fun initialize(genreList : List<GenreResponse>?){
        if(genreSet.isNotEmpty()) return
        val list : MutableList<GenreResponse>? = genreList?.toMutableList()
        list?.add(0,GenreResponse(0,"All"))
        val unselectedValue = list?.firstOrNull { it.id == 0 } ?: return
        unselected = Genre.Unselected(unselectedValue.id)
        genreSet.add(unselected)
    }
}