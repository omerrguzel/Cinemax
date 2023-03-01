package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemax.R
import com.example.cinemax.data.entity.movielist.MovieItemResponse
import com.example.cinemax.databinding.ItemUpcomingBinding
import java.text.SimpleDateFormat

class MovieListAdapter : PagingDataAdapter<MovieItemResponse,
        MovieListAdapter.MovieListViewHolder
        >(MoviesComparator) {

    val movieClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder {
        return MovieListViewHolder(
            ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("dd MMM yyyy")
            val outputDateStr: String =
                outputFormat.format(inputFormat.parse(moviesItem.releaseDate))

            textViewUpcomingMovieTitle.text = moviesItem.title
            textViewUpcomingReleaseDate.text = "Release Date: $outputDateStr"
            Glide.with(imageViewUpcoming.context)
                .load("https://image.tmdb.org/t/p/w500" + moviesItem.backdropPath)
                .apply (
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.error)
                        )
                .into(imageViewUpcoming)
        }
    }

    object MoviesComparator : DiffUtil.ItemCallback<MovieItemResponse>() {
        override fun areItemsTheSame(
            oldItem: MovieItemResponse,
            newItem: MovieItemResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieItemResponse,
            newItem: MovieItemResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}