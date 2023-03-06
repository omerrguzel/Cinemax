package com.example.cinemax.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemax.data.entity.tvdetail.CrewItem
import com.example.cinemax.databinding.ItemCastBinding
import com.example.cinemax.utils.showImage


class CrewAdapter(
    private var crewList: List<CrewItem>
) : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewViewHolder {
        return CrewViewHolder(
            ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return crewList.size
    }


    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        val crew = crewList[position]
        holder.bindCrew(crew,position)
    }

    inner class CrewViewHolder(private val binding: ItemCastBinding,) :
        ViewHolder(binding.root) {

        fun bindCrew(crewItem: CrewItem,position: Int) = with(binding) {
            println("Crew Info $crewList ")
            imageViewCast.showImage(crewItem.crewProfilePath ?: "")
            textViewCastName.text = crewItem.crewName ?: ""
            textViewCastRole.text = crewItem.crewJob ?: ""
        }

    }


    fun setData(newList: List<CrewItem>?) {
        if (newList != null) {
            crewList = newList
            notifyDataSetChanged()
        }
    }

}
