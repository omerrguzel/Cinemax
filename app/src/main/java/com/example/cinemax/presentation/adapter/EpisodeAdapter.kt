package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemax.data.entity.tvdetail.TvEpisodeItemResponse
import com.example.cinemax.databinding.ItemEpisodeBinding
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.makeExpandable
import com.example.cinemax.utils.showImage


class EpisodeAdapter(
    private var episodeList: List<TvEpisodeItemResponse>
) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeViewHolder {
        return EpisodeViewHolder(
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }


    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodeList[position]
        holder.bindEpisode(episode,position)
    }

    inner class EpisodeViewHolder(private val binding: ItemEpisodeBinding,) :
        ViewHolder(binding.root) {

        fun bindEpisode(episodeItemResponse: TvEpisodeItemResponse,position: Int) = with(binding) {
            println("Episode Info $episodeList ")
            imageViewEpisode.showImage(episodeItemResponse.stillPath?: "")
            textViewEpisodeNumber.text = episodeItemResponse.episodeName
            textViewEpisodeRuntime.text= episodeItemResponse.runtime.toString()+"\"\""
            textViewEpisodeOverView.makeExpandable(episodeItemResponse.overviewEpisode.toString())
            if(episodeItemResponse.overviewEpisode == ""){
                textViewEpisodeOverView.gone()
            }
        }

    }


    fun setData(newList: List<TvEpisodeItemResponse>?) {
        if (newList != null) {
            episodeList = newList
            notifyDataSetChanged()
        }
    }

}
