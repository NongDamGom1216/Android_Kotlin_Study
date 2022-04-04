package com.example.basic_recyclerview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainDataPagerAdapter(fa: FragmentActivity, val mainDataList: List<MainData>) : FragmentStateAdapter(fa) {

    override fun getItemCount() = mainDataList.size

    override fun createFragment(position: Int): Fragment {
        return MainDataFragment.newInstance(mainDataList[position])
    }

}