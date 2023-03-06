package com.example.cinemax.utils

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemax.R
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import kotlin.math.roundToInt


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

fun ImageView.showProfileImage(imgUrl : String){
    Glide.with(context)
        .load(imgUrl)
        .apply (
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.error)
        )
        .into(this)
}

fun String.showOnlyYear(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("yyyy")
    return outputFormat.format(inputFormat.parse(this))
}

fun Double.roundRating(): String {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble().toString()
}

fun TextView.makeExpandable(text: String?) {

    maxLines = 2
    ellipsize = TextUtils.TruncateAt.END
    setText(text)

    setOnClickListener {
        val isExpanded = maxLines == Integer.MAX_VALUE
        if (isExpanded) {
            maxLines = 2
            ellipsize = TextUtils.TruncateAt.END
        } else {
            maxLines = Integer.MAX_VALUE
            ellipsize = null
        }
    }
}

fun LifecycleOwner.observe(state: Lifecycle.State = Lifecycle.State.RESUMED, action: suspend () -> Unit) {
    lifecycleScope.launch {
        this@observe.repeatOnLifecycle(state) {
            action()
        }
    }
}






