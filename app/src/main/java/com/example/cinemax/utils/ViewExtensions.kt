package com.example.cinemax.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller


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

