package com.example.cinemax.presentation.adapter

import android.provider.MediaStore.Audio.Media
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cinemax.R
import com.example.cinemax.data.entity.movielist.MovieItemUIModel
import com.example.cinemax.data.entity.search.SearchMediaItemUIModel
import com.example.cinemax.data.entity.search.SearchPersonItemUIModel
import com.example.cinemax.databinding.ItemActorBinding
import com.example.cinemax.databinding.ItemMovieGenericBinding
import com.example.cinemax.utils.showImage

class SearchResultPersonAdapter : PagingDataAdapter<SearchPersonItemUIModel,
        SearchResultPersonAdapter.PersonListViewHolder>(PersonComparator) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonListViewHolder {
        return PersonListViewHolder(
            ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PersonListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindMedia(it) }
    }

    inner class PersonListViewHolder(private val binding: ItemActorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindMedia(personItem: SearchPersonItemUIModel) {
            binding.apply {
                textViewActorName.text = personItem.actorName
                imageViewActor.showImage(personItem.actorImage.toString())
            }
        }

    }


    object PersonComparator : DiffUtil.ItemCallback<SearchPersonItemUIModel>() {
        override fun areItemsTheSame(
            oldItem: SearchPersonItemUIModel,
            newItem: SearchPersonItemUIModel
        ): Boolean {
            return oldItem.actorName == newItem.actorName
        }

        override fun areContentsTheSame(
            oldItem: SearchPersonItemUIModel,
            newItem: SearchPersonItemUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}

