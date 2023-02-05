package com.example.beeruthus.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beeruthus.ui.BeerFragment
import com.example.beeruthus.ui.FavoriteFragment

private const val NUM_TABS = 2
class BeerPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BeerFragment()
            else -> FavoriteFragment()
        }
    }
}