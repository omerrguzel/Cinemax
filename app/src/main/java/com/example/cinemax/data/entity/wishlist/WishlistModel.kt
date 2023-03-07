package com.example.cinemax.data.entity.wishlist

import com.example.cinemax.data.entity.moviedetail.GenreResponse

data class WishlistModel(
    var id : Int?=null,
    var title : String?= null,
    var mediaType : String?= null,
    var voteAverage : Double?= null,
    var backdropPath : String? = null,
    var genre : String? = null

)
