package com.ysdc.comet.common.ui.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class GenericFragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return fragments.size
    }

    private val fragments = ArrayList<Fragment>()

    // Our custom method that populates this Adapter with Fragments
    fun addFragments(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}