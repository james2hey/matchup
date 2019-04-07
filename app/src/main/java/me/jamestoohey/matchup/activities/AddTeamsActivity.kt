package me.jamestoohey.matchup.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.adapters.TabAdapter

class AddTeamsActivity: AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var tabAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_add_teams)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        tabAdapter = TabAdapter(supportFragmentManager, intent.extras)
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}
