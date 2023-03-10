package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.R
import com.example.cinemax.data.entity.video.MovieVideoItemResponse
import com.example.cinemax.data.entity.video.MovieVideoResponse
import com.example.cinemax.databinding.ItemVideoBinding


class VideoListAdapter(
    private var videoList: List<MovieVideoItemResponse>
) : RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder>() {

    var videoSelectListener: ((key: String?) -> Unit)? = null
    var selectedItemPos = 0
    var lastItemSelectedPos = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideoListViewHolder {
        return VideoListViewHolder(
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return videoList.size
    }


    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        val video = videoList[position]
        holder.bindVideo(video, position)
    }

    inner class VideoListViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindVideo(video: MovieVideoItemResponse, position: Int) = with(binding) {

            if(position == selectedItemPos){
                imageViewPlay.setColorFilter(this.imageViewPlay.context.resources.getColor(R.color.orange))
            }
            else{
                imageViewPlay.setColorFilter(this.imageViewPlay.context.resources.getColor(R.color.blue_accent))
            }

            textViewVideoTitle.text = video.name
            cardViewVideoTitle.setOnClickListener {
                videoSelectListener?.invoke(video.key)
                selectedItemPos = position
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


    fun setData(newList: List<MovieVideoItemResponse>?) {
        if (newList != null) {
            videoList = newList
            notifyDataSetChanged()
        }
    }
}