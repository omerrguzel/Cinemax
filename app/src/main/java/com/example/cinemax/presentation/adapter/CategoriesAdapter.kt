package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.data.entity.moviedetail.Genre
import com.example.cinemax.data.entity.moviedetail.GenreList
import com.example.cinemax.databinding.ItemGenresBinding

class CategoriesAdapter(
    private var genreList : GenreList,
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
        return genreList.genres.size
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        genreList.genres[position].let {
            holder.bindGenre(it)
        }
    }

    inner class GenreListViewHolder(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bindGenre(genre: Genre) = with(binding){
                chipGenres.text = genre.name
            }
        }

    fun setData(newList: GenreList?) {
        if (newList != null) {
            genreList = newList
        }
        notifyDataSetChanged()
    }
}