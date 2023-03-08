package com.example.cinemax.presentation.wishlist

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cinemax.data.entity.wishlist.WishlistModel
import com.example.cinemax.databinding.FragmentWishlistBinding
import com.example.cinemax.presentation.adapter.CrewAdapter
import com.example.cinemax.presentation.adapter.WishlistAdapter
import com.example.cinemax.presentation.detail.DetailFragment.Companion.WISHL
import com.example.cinemax.presentation.search.SearchFragmentDirections
import com.example.cinemax.utils.SharedPrefManager
import com.example.cinemax.utils.show

class WishlistFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding
    private var wishList: MutableList<WishlistModel> = mutableListOf()
    private lateinit var sharedPrefManager: SharedPrefManager
    private var wishlistAdapter: WishlistAdapter = WishlistAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefManager = SharedPrefManager(this.requireActivity())
        readWishListData()
        setRemoveWishButton()
        setClickMediaItem()
    }


    private fun readWishListData() {
        sharedPrefManager = SharedPrefManager(this.requireActivity())
        if (sharedPrefManager.ifContains(WISHL) == true) {
            wishList = sharedPrefManager.readWishList(WISHL).toMutableList()
            setWishListAdapter(wishList)
        }
    }

    private fun setWishListAdapter(wishList: List<WishlistModel>) {
        wishlistAdapter.setData(wishList)
        if(wishlistAdapter.itemCount == 0) binding.viewNoWish.root.show()
        binding.recyclerViewWishlist.adapter = wishlistAdapter
    }


    private fun setRemoveWishButton() {
        wishlistAdapter.removeWishClickListener = {
            removeWishlistFromSharedPref(it)
        }
    }

    private fun setClickMediaItem(){
        wishlistAdapter.itemClickListener = {
            navigateToDetail(it)
        }
    }

    private fun removeWishlistFromSharedPref(wishlistModel: WishlistModel) {
        if (sharedPrefManager.ifContains(WISHL) == true) {
            wishList = sharedPrefManager.readWishList(WISHL).toMutableList()
        }
        wishList.remove(
            WishlistModel(
                id = wishlistModel.id,
                title = wishlistModel.title,
                mediaType = wishlistModel.mediaType,
                voteAverage = wishlistModel.voteAverage,
                backdropPath = wishlistModel.backdropPath,
                genre = wishlistModel.genre
            )
        )
        sharedPrefManager.writeWishList(WISHL, wishList.toTypedArray())
        setWishListAdapter(wishList)
        Toast.makeText(context, "remove from wishlist", Toast.LENGTH_SHORT).show()
        Log.v(ContentValues.TAG, "removed from wishList: $wishList")
    }

    private fun navigateToDetail(wishlistModel: WishlistModel) {
        findNavController().navigate(
            WishlistFragmentDirections.actionWishlistFragmentToDetailFragment(
                wishlistModel.mediaType.toString(),
                wishlistModel.id ?:0
            )
        )
    }
}