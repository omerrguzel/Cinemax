package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.R
import com.example.cinemax.data.entity.search.SearchMediaItemUIModel
import com.example.cinemax.databinding.ItemMovieGenericBinding
import com.example.cinemax.utils.roundRating
import com.example.cinemax.utils.showImage

class SearchResultMediaAdapter(
    diffCallback : DiffUtil.ItemCallback<SearchMediaItemUIModel
            >) : PagingDataAdapter<SearchMediaItemUIModel,
        SearchResultMediaAdapter.MediaListViewHolder>(diffCallback) {

    var mediaClickListener: ((mediaItem : SearchMediaItemUIModel) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MediaListViewHolder {
        return MediaListViewHolder(
            ItemMovieGenericBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MediaListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindMedia(it) }
    }

    inner class MediaListViewHolder(private val binding: ItemMovieGenericBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMedia(mediaItem: SearchMediaItemUIModel) {
            binding.apply {
                textViewRatingGeneric.text = mediaItem.voteAverage?.roundRating()
                if(mediaItem.mediaType == MOVIE_MEDIA_TYPE){
                    textViewTitle.text = mediaItem.movieName
                    textViewReleaseDate.text = mediaItem.releaseDate
                    textViewMediaType.text = root.context.getString(R.string.movie)
                    textViewGenre.text = mediaItem.genreMovieIds?.get(0)
                }else{
                    textViewTitle.text = mediaItem.seriesName
                    textViewReleaseDate.text = mediaItem.firstAirDate
                    textViewMediaType.text = root.context.getString(R.string.series)
                    textViewGenre.text = mediaItem.genreTvIds?.get(0)
                }
                imageViewGenericPoster.showImage(mediaItem.posterPath.toString())
                cardViewMovieGeneric.setOnClickListener {
                    mediaClickListener?.invoke(mediaItem)
                }
            }
        }
    }


    object MediaComparator : DiffUtil.ItemCallback<SearchMediaItemUIModel>() {
        override fun areItemsTheSame(
            oldItem: SearchMediaItemUIModel,
            newItem: SearchMediaItemUIModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchMediaItemUIModel,
            newItem: SearchMediaItemUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object{
        const val MOVIE_MEDIA_TYPE = "movie"
    }
}

