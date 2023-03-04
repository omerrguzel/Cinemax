package com.example.cinemax.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemax.R


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun RecyclerView.scrollToStart(context: Context): SmoothScroller {
    val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
    return smoothScroller
}

fun ImageView.showImage(imgUrl : String){
    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w500$imgUrl")
        .apply (
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.error)
        )
        .into(this)
}

