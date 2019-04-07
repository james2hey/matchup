package me.jamestoohey.matchup.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import me.jamestoohey.matchup.fragments.AddBatchTeamsFragment
import me.jamestoohey.matchup.fragments.AddTeamFragment

class TabAdapter(fm: FragmentManager?, bundle: Bundle?) : FragmentStatePagerAdapter(fm) {
    private var fragmentList: List<Fragment> = arrayListOf(
        AddTeamFragment().apply { setBundle(bundle) },
        AddBatchTeamsFragment()
    )
    private var fragmentTitleList: List<String> = arrayListOf("Single", "Batch")

    override fun getItem(i: Int): Fragment = fragmentList[i]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(i: Int): CharSequence? = fragmentTitleList[i]

}