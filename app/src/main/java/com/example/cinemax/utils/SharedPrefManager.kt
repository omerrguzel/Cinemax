package com.example.cinemax.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.cinemax.data.entity.wishlist.WishlistModel
import com.google.gson.Gson


class SharedPrefManager {

    private var sharedPref: SharedPreferences? = null

    constructor(activity: Activity) {
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
    }

    fun ifContains(key: String): Boolean? {
        return sharedPref?.contains(key)
    }

    fun ifContainsId(key: String, id: Int): Boolean {
        return sharedPref?.contains(id.toString()) == true && sharedPref?.contains(key) == true
    }

    fun readLogData(key: String): String? {
        return  sharedPref?.getString(key, "0")
    }

    fun writeLogData(key: String, movieId: Int): Boolean {
        sharedPref?.let {
            with(it.edit()) {
                putString(key, movieId.toString())
                apply()
                return commit()
            }
        }
        return false
    }


    fun readWishList(key: String): Array<WishlistModel> {
        val gson = Gson()
        val jsonText: String = sharedPref?.getString(key, null).toString()
        return gson.fromJson(
            jsonText,
            Array<WishlistModel>::class.java
        )
    }

    fun writeWishList(key: String, value: Array<WishlistModel>): Boolean {
        val gson = Gson()
        val jsonText = gson.toJson(value)
        sharedPref?.let {
            with(it.edit()) {
                putString(key, jsonText)
                apply()
                return commit()
            }
        }
        return false
    }
}