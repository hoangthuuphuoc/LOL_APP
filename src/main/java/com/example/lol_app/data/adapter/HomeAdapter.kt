package com.example.lol_app.data.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lol_app.ui.fragment.home.HomeFragment
import com.example.lol_app.ui.fragment.search.SearchFragment

class HomeAdapter(
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(p0: Int): Fragment {
        return when (p0) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            else -> HomeFragment()
        }
    }


}