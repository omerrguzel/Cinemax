package com.example.cinemax.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemax.R

class ViewExtensions {

    fun ImageView.downloadFromUrl(imgPath: String?) {
        imgPath?.let {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500$imgPath")
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(this)
        }
    }

    @BindingAdapter("android:downloadImageUrl")
    fun downloadImage(view: ImageView, imgPath: String?) {
        view.downloadFromUrl(imgPath)
    }

}