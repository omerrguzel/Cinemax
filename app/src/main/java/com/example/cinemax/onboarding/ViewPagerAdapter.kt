package com.example.cinemax.onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cinemax.R


class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val context: Context
) :
    FragmentStateAdapter(fm,lifecycle ) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ViewPagerFragment.newInstance(
                context.resources.getString(R.string.header_onboarding_first),
                context.resources.getString(R.string.body_onboarding_first),
                R.drawable.onboarding_first,
                R.drawable.button_onboarding_first
            )
            1 -> ViewPagerFragment.newInstance(
                context.resources.getString(R.string.header_onboarding_second),
                context.resources.getString(R.string.body_onboarding_second),
                R.drawable.onboarding_second,
                R.drawable.button_onboarding_second
            )
            else -> ViewPagerFragment.newInstance(
                context.resources.getString(R.string.header_onboarding_third),
                context.resources.getString(R.string.body_onboarding_third),
                R.drawable.onboarding_third,
                R.drawable.button_onboarding_third
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}