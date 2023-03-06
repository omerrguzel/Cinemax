package com.example.cinemax.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinemax.R
import com.example.cinemax.data.entity.profile.ProfileModel
import com.example.cinemax.databinding.FragmentProfileBinding
import com.example.cinemax.presentation.adapter.ProfileAdapter
import com.example.cinemax.utils.showProfileImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var accountListAdapter : ProfileAdapter = ProfileAdapter(arrayListOf())
    private var generalListAdapter : ProfileAdapter = ProfileAdapter(arrayListOf())
    private var moreListAdapter : ProfileAdapter = ProfileAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewAdapters()
        setUserView()

    }

    fun setUserView(){
        val auth : FirebaseAuth = Firebase.auth
        binding.apply {
            imageViewProfile.showProfileImage(auth.currentUser?.photoUrl.toString())
            textViewUserName.text = auth.currentUser?.displayName
            textViewEmail.text = auth.currentUser?.email
        }
    }

    fun bindViewAdapters(){
        val accountViewList = listOf<ProfileModel>(
            ProfileModel(R.drawable.ic_member, getString(R.string.member)),
            ProfileModel(R.drawable.ic_padlock, getString(R.string.changePassword))
        )

        accountListAdapter.setData(accountViewList)
        binding.recyclerViewAccount.adapter = accountListAdapter

        val generalViewList = listOf<ProfileModel>(
            ProfileModel(R.drawable.ic_notification, getString(R.string.notification)),
            ProfileModel(R.drawable.ic_language, getString(R.string.language)),
            ProfileModel(R.drawable.ic_country, getString(R.string.country)),
            ProfileModel(R.drawable.ic_clear_cache, getString(R.string.clearCache)),
        )

        generalListAdapter.setData(generalViewList)
        binding.recyclerViewGeneral.adapter = generalListAdapter

        val moreViewList = listOf<ProfileModel>(
            ProfileModel(R.drawable.ic_legal, getString(R.string.legalPolicy)),
            ProfileModel(R.drawable.ic_help, getString(R.string.helpFeedback)),
            ProfileModel(R.drawable.ic_about, getString(R.string.aboutUs)),
        )

        moreListAdapter.setData(moreViewList)
        binding.recyclerViewMore.adapter = moreListAdapter
    }
}