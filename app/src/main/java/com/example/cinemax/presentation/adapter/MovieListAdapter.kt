package com.example.cinemax.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.data.entity.movielist.MovieItemResponse
import com.example.cinemax.data.entity.movielist.MoviesResponse
import com.example.cinemax.databinding.ItemUpcomingBinding

class MovieListAdapter (
    private var movieList: ArrayList<MovieItemResponse> = ArrayList(),
        ) : PagingDataAdapter<MovieItemResponse,
        MovieListAdapter.MovieListViewHolder
        >(MoviesComparator) {

    val movieClickListener : (() -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder {
        return MovieListViewHolder(
            ItemUpcomingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindMovies(it) }
    }

    inner class MovieListViewHolder(private val binding: ItemUpcomingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMovies(moviesItem: MovieItemResponse) = with(binding) {

            cardViewUpcomingMovie.setOnClickListener { movieClickListener?.invoke() }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    object MoviesComparator : DiffUtil.ItemCallback<MovieItemResponse>() {
        override fun areItemsTheSame(oldItem: MovieItemResponse, newItem: MovieItemResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItemResponse, newItem: MovieItemResponse): Boolean {
            return oldItem == newItem
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieList(movieList: List<MovieItemResponse>){
        this.movieList.clear()
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }
}