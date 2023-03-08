package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemax.R
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.databinding.ItemGenresBinding


class CategoriesAdapter(
    private var genreListResponse: List<GenreResponse>
) : RecyclerView.Adapter<CategoriesAdapter.GenreListViewHolder>() {

    var filterMoviesByGenre : ((id:Int?) -> Unit)? = null
    var selectedItemPos = 0
    var lastItemSelectedPos = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreListViewHolder {
        return GenreListViewHolder(
            ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return genreListResponse.size
    }


    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        val genre = genreListResponse[position]
        holder.bindGenre(genre, position)
    }

    inner class GenreListViewHolder(private val binding: ItemGenresBinding) :
        ViewHolder(binding.root) {

        fun bindGenre(genre: GenreResponse, position: Int) = with(binding) {
            chipGenres.text = genre.name
            if (position == selectedItemPos) {
                chipGenres.setChipBackgroundColorResource(R.color.soft)
                chipGenres.setTextColor(this.chipGenres.context.resources.getColor(R.color.blue_accent))
            } else {
                chipGenres.setChipBackgroundColorResource(R.color.dark)
                chipGenres.setTextColor(this.chipGenres.context.resources.getColor(R.color.white))
            }
            chipGenres.setOnCheckedChangeListener { _, _ ->
                selectedItemPos = position
                filterMoviesByGenre?.invoke(genre.id)
                if (lastItemSelectedPos == -1)
                    lastItemSelectedPos = selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }
    }


    fun setData(newList: List<GenreResponse>?) {
        if (newList != null) {
            genreListResponse = newList
            notifyDataSetChanged()
        }
    }

}
