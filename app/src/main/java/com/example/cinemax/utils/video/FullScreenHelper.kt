package com.example.cinemax.utils.video

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


class FullScreenHelper(private val context: Activity, vararg views: View) {
    private val views: Array<View>

    /**
     * @param context
     * @param views to hide/show
     */
    init {
        this.views = views as Array<View>
    }

    /**
     * call this method to enter full screen
     */
    fun enterFullScreen() {
        val window = context.window
        hideSystemUI(window)
        for (view: View in views) {
            view.visibility = View.GONE
            view.invalidate()
        }
    }

    /**
     * call this method to exit full screen
     */
    fun exitFullScreen() {
        val window = context.window
        showSystemUI(window)
        for (view: View in views) {
            view.visibility = View.VISIBLE
            view.invalidate()
        }
    }

    private fun hideSystemUI(window : Window) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI(window : Window) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.systemBars())
    }

}