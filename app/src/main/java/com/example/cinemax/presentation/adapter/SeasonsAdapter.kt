package com.example.cinemax.presentation.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemax.R
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.tvdetail.SeasonInfoResponse
import com.example.cinemax.databinding.ItemGenresBinding
import com.example.cinemax.databinding.ItemSeasonBinding


class SeasonsAdapter(
    var seasonInfoList: List<SeasonInfoResponse>
) : RecyclerView.Adapter<SeasonsAdapter.SeasonListViewHolder>() {

    var selectSeasonListener: ((season: SeasonInfoResponse?) -> Unit)? = null
    var lastPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeasonListViewHolder {
        return SeasonListViewHolder(
            ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return seasonInfoList.size
    }

    override fun onBindViewHolder(holder: SeasonListViewHolder, position: Int) {
        val season = seasonInfoList[position]
        holder.bindSeason(season, position)
    }

    inner class SeasonListViewHolder(private val binding: ItemSeasonBinding) :
        ViewHolder(binding.root) {

        fun bindSeason(seasonItem: SeasonInfoResponse, position: Int) = with(binding) {
            textViewSeasonItem.text = seasonItem.name
            if (position == lastPosition){
                textViewSeasonItem.setTextColor(root.context.resources.getColor(R.color.white))
                textViewSeasonItem.setTextSize(TypedValue.COMPLEX_UNIT_PX,root.context.resources.getDimensionPixelSize(R.dimen._14ssp).toFloat())
            }
            textViewSeasonItem.setOnClickListener {
                selectSeasonListener?.invoke(seasonItem)
            }
        }
    }

    fun setSelectedItem(seasonNumber: Int) {
        println(seasonNumber)
        lastPosition = seasonNumber

    }

    fun setData(newList: List<SeasonInfoResponse>?) {
        if (newList != null) {
            seasonInfoList = newList
            notifyDataSetChanged()
        }
    }
}
