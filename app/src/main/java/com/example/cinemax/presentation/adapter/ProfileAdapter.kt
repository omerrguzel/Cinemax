package com.example.cinemax.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.cinemax.R
import com.example.cinemax.data.entity.moviedetail.GenreResponse
import com.example.cinemax.data.entity.profile.ProfileModel
import com.example.cinemax.databinding.ItemGenresBinding
import com.example.cinemax.databinding.ItemProfileCardsBinding


class ProfileAdapter(
    private var profileList: List<ProfileModel>
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    var profileItemClickLListener : ((id:Int?) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewHolder {
        return ProfileViewHolder(
            ItemProfileCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return profileList.size
    }


    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profileItem = profileList[position]
        holder.bindProfile(profileItem, position)
    }

    inner class ProfileViewHolder(private val binding: ItemProfileCardsBinding) :
        ViewHolder(binding.root) {

        fun bindProfile(profileModel: ProfileModel, position: Int) = with(binding) {
            imageViewLogoMember.setImageDrawable(ContextCompat.getDrawable(binding.root.context,profileModel.resId))
            textViewProfileItem.text=profileModel.profileString
        }
    }


    fun setData(newList: List<ProfileModel>?) {
        if (newList != null) {
            profileList = newList
            notifyDataSetChanged()
        }
    }

}
