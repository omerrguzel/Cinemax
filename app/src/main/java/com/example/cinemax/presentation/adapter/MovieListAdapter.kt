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
import com.example.cinemax.data.entity.movielist.MovieItemUIModel
import com.example.cinemax.databinding.ItemMostpopularhomeBinding
import com.example.cinemax.databinding.ItemUpcomingBinding
import com.example.cinemax.utils.showImage
import java.text.SimpleDateFormat

class MovieListAdapter : PagingDataAdapter<MovieItemUIModel,
        RecyclerView.ViewHolder
        >(MoviesComparator) {

    val movieClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_UPCOMING -> MovieListViewHolder(
                ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            VIEW_TYPE_POPULAR -> MostPopularViewHolder(
                ItemMostpopularhomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("There is no ViewHolder to inflate for type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieListViewHolder -> getItem(position)?.let { holder.bindMovies(it) }
            is MostPopularViewHolder -> getItem(position)?.let { holder.bindMovies(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)?.viewType) {
            0 -> VIEW_TYPE_UPCOMING
            1 -> VIEW_TYPE_POPULAR
            else -> throw IllegalArgumentException("There is no ViewType")
        }
    }


    inner class MovieListViewHolder(private val binding: ItemUpcomingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMovies(moviesItem: MovieItemUIModel) = with(binding) {
            cardViewUpcomingMovie.setOnClickListener { movieClickListener?.invoke() }

            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat = SimpleDateFormat("dd MMM yyyy")
            val outputDateStr: String =
                outputFormat.format(inputFormat.parse(moviesItem.releaseDate))

            textViewUpcomingMovieTitle.text = moviesItem.title
            textViewUpcomingReleaseDate.text = "Release Date: $outputDateStr"
            imageViewUpcoming.showImage(moviesItem.backdropPath)
        }
    }

    inner class MostPopularViewHolder(private val binding: ItemMostpopularhomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMovies(moviesItem: MovieItemUIModel) = with(binding) {
            textViewTitleMovie.text = moviesItem.title
            textViewGenre.text = moviesItem.genreIds?.get(0) ?: ""
            textViewRatingMostPopular.text = moviesItem.voteAverage.toString()
            imageViewMostPopularPoster.showImage(moviesItem.posterPath)
        }
    }

    object MoviesComparator : DiffUtil.ItemCallback<MovieItemUIModel>() {
        override fun areItemsTheSame(
            oldItem: MovieItemUIModel,
            newItem: MovieItemUIModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieItemUIModel,
            newItem: MovieItemUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val VIEW_TYPE_UPCOMING = 0
        const val VIEW_TYPE_POPULAR = 1
    }
}