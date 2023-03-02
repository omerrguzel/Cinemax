package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.moviedetail.GenreListResponse
import com.example.cinemax.databinding.ItemGenresBinding

class CategoriesAdapter(
    private var genreListResponse: GenreListResponse,
    private var checkedPosition: Int = -1
//    private val filterMoviesByGenre : (() -> Unit)? = null
) : RecyclerView.Adapter<CategoriesAdapter.GenreListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreListViewHolder {
        return GenreListViewHolder(
            ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return genreListResponse.genres.size
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        genreListResponse.genres[position].let {
            holder.bindGenre(it, position+1)
        }
    }

    inner class GenreListViewHolder(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindGenre(genre: GenreResponse, position: Int) = with(binding) {
            if (position == 0) {
                chipGenres.text = "All"
            } else chipGenres.text = genre.name

        }
    }

    fun setData(newList: GenreListResponse?) {
        if (newList != null) {
            genreListResponse = newList
        }
        notifyDataSetChanged()
    }
}