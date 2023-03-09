package com.example.cinemax.presentation.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cinemax.R
import com.example.cinemax.data.entity.profile.ProfileModel
import com.example.cinemax.databinding.FragmentProfileBinding
import com.example.cinemax.presentation.adapter.ProfileAdapter
import com.example.cinemax.utils.showProfileImage
import com.example.cinemax.utils.showSnack
import com.facebook.AccessToken
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var accountListAdapter: ProfileAdapter = ProfileAdapter(arrayListOf())
    private var generalListAdapter: ProfileAdapter = ProfileAdapter(arrayListOf())
    private var moreListAdapter: ProfileAdapter = ProfileAdapter(arrayListOf())
    private val logOutDialog: LogOutDialog = LogOutDialog()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewAdapters()
        setUserView()
        setLogOutButton()
    }

    private fun setUserView() {
        val auth: FirebaseAuth = Firebase.auth
        binding.apply {
            imageViewProfile.showProfileImage(
                "${auth.currentUser?.photoUrl}?access_token=${AccessToken.getCurrentAccessToken()?.token}"
            )
            textViewUserName.text = auth.currentUser?.displayName
            textViewEmail.text = auth.currentUser?.email
        }
    }

    private fun bindViewAdapters() {
        val accountViewList = listOf(
            ProfileModel(R.drawable.ic_member, getString(R.string.member)),
            ProfileModel(R.drawable.ic_padlock, getString(R.string.changePassword))
        )

        accountListAdapter.setData(accountViewList)
        binding.recyclerViewAccount.adapter = accountListAdapter

        val generalViewList = listOf(
            ProfileModel(R.drawable.ic_notification, getString(R.string.notification)),
            ProfileModel(R.drawable.ic_language, getString(R.string.language)),
            ProfileModel(R.drawable.ic_country, getString(R.string.country)),
            ProfileModel(R.drawable.ic_clear_cache, getString(R.string.clearCache)),
        )

        generalListAdapter.setData(generalViewList)
        binding.recyclerViewGeneral.adapter = generalListAdapter

        val moreViewList = listOf(
            ProfileModel(R.drawable.ic_legal, getString(R.string.legalPolicy)),
            ProfileModel(R.drawable.ic_help, getString(R.string.helpFeedback)),
            ProfileModel(R.drawable.ic_about, getString(R.string.aboutUs)),
        )

        moreListAdapter.setData(moreViewList)
        binding.recyclerViewMore.adapter = moreListAdapter

        setNavForItems()
    }

    private fun setLogOutButton() {
        binding.buttonLogOut.setOnClickListener {
            childFragmentManager.let { logOutDialog.show(it, null) }
        }
    }

    private fun setNavForItems() {
        accountListAdapter.profileItemClickLListener = { it ->
            if (it == 0) {
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
        }
        generalListAdapter.profileItemClickLListener = { it ->
            when (it) {
                1 -> findNavController().navigate(R.id.action_profileFragment_to_languageFragment)
                2 -> findNavController().navigate(R.id.action_profileFragment_to_countryFragment)
                3 -> {
                    context?.cacheDir?.deleteRecursively()
                    requireView().showSnack("Cache is ")
                }

            }
        }
        moreListAdapter.profileItemClickLListener = { it ->
            if (it == 0) {
                findNavController().navigate(R.id.action_profileFragment_to_policyFragment)
            }
        }
    }
}