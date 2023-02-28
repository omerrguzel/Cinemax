package com.example.cinemax.utils

import com.example.cinemax.data.entity.movielist.MovieItemResponse
import java.text.SimpleDateFormat

class DateUtils {

    fun getDateToFormat(movieItemResponse: MovieItemResponse): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("dd MMM yyyy")
        return outputFormat.format(inputFormat.parse(movieItemResponse.releaseDate))
    }
}