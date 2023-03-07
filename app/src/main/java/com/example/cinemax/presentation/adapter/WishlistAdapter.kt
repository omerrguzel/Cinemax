package com.example.cinemax.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemax.R
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.wishlist.WishlistModel
import com.example.cinemax.databinding.ItemGenresBinding
import com.example.cinemax.databinding.ItemWishlistBinding
import com.example.cinemax.utils.roundRating
import com.example.cinemax.utils.show
import com.example.cinemax.utils.showImage


class WishlistAdapter(
    private var wishListResponse: List<WishlistModel>
) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    var removeWishClickListener: ((wishlistItem: WishlistModel) -> Unit)? = null
    var itemClickListener: ((wishlistItem: WishlistModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistViewHolder {
        return WishlistViewHolder(
            ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return wishListResponse.size
    }


    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val wishlistItem = wishListResponse[position]
        holder.bindWishlistItem(wishlistItem, position)
    }

    inner class WishlistViewHolder(private val binding: ItemWishlistBinding) :
        ViewHolder(binding.root) {

        fun bindWishlistItem(wishlistItem: WishlistModel, position: Int) = with(binding) {
            imageViewWishlistItem.showImage(wishlistItem.backdropPath.toString() ?: "")
            textViewGenreWishlistItem.text = wishlistItem.genre
            textViewRatingWishlistItem.text = wishlistItem.voteAverage?.roundRating()
            textViewTitleWishlistItem.text = wishlistItem.title
            textViewMediaTypeWishlistItem.text = wishlistItem.mediaType
            imageViewUnwishButton.setOnClickListener {
                removeWishClickListener?.invoke(wishlistItem)
            }
            cardViewWishListItem.setOnClickListener {
                itemClickListener?.invoke(wishlistItem)
            }

        }

    }

    fun setData(newList: List<WishlistModel>?) {
        if (newList != null) {
            wishListResponse = newList
            notifyDataSetChanged()
        }
    }

}
