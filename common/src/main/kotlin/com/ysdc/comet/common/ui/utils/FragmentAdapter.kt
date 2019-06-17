package com.ysdc.comet.common.ui.utils

import com.ysdc.comet.common.ui.base.BaseFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return fragments.size
    }

    private val fragments = ArrayList<BaseFragment>()

    // Our custom method that populates this Adapter with Fragments
    fun addFragments(fragment: BaseFragment) {
        fragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    fun getItemAtPosition(position: Int): BaseFragment {
        return fragments[position]
    }

}