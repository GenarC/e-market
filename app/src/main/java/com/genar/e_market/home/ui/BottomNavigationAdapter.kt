package com.genar.e_market.home.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class BottomNavigationAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val bottomNavAdapterList = arrayListOf(
        HomeFragment.newInstance()
    )

    override fun getItemCount(): Int = bottomNavAdapterList.size

    override fun createFragment(position: Int): Fragment = bottomNavAdapterList[position]

    fun getFragment(position: Int): Fragment = bottomNavAdapterList[position]

    companion object {
        const val HOME_PAGE_INDEX = 0
    }
}