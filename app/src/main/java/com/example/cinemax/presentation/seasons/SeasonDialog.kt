package com.example.cinemax.presentation.seasons

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemax.data.entity.tvdetail.SeasonInfoResponse
import com.example.cinemax.databinding.DialogSeasonsBinding
import com.example.cinemax.presentation.adapter.SeasonsAdapter
import com.example.cinemax.utils.Resource
import com.example.cinemax.utils.gone
import com.example.cinemax.utils.show
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SeasonDialog() : DialogFragment() {

    private lateinit var binding: DialogSeasonsBinding

    private val viewModel: SeasonDialogViewModel by viewModels()
    private var seasonsAdapter: SeasonsAdapter = SeasonsAdapter(arrayListOf())

    var passSelectedSeasonListener : ((season: SeasonInfoResponse?) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Objects.requireNonNull(dialog)?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        binding = DialogSeasonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        getSeasonDetails()
        binding.closeButton.setOnClickListener {
            dismiss()
        }
        getSelectedSeason()
    }

    private fun getSeasonDetails() {
        val mArgs = arguments
        val tvId = mArgs!!.getInt("ID")
        viewModel.getTVDetailResult(tvId).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.ERROR -> {
                    Log.d(ContentValues.TAG, "Fetch Info Error: ${it.message}")
                }
                Resource.Status.SUCCESS -> {
                    Log.d(ContentValues.TAG, "TvDetailResult: ${it.data}")
                    binding.progressBar.gone()
                    binding.apply {
                        seasonsAdapter.setData(it.data?.seasonInfoList)
                        recyclerViewSeasons.adapter = seasonsAdapter
                        recyclerViewSeasons.setHasFixedSize(true)
                        recyclerViewSeasons.layoutManager =
                            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
                        arguments?.getInt("LAST_SEASON_NUMBER")
                            ?.let { selectedNumber -> seasonsAdapter.setSelectedItem(selectedNumber) }

                    }
                }
            }
        }
    }

    private fun getSelectedSeason() {
        seasonsAdapter.selectSeasonListener = {
            passSelectedSeasonListener?.invoke(it)
            dialog?.dismiss()

        }
    }
}