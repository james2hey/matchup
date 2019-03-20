package me.jamestoohey.matchup.activities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import me.jamestoohey.matchup.MatchModel
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.adapters.MatchAdapter
import me.jamestoohey.matchup.data.entity.Match
import me.jamestoohey.matchup.viewmodel.MatchViewModel

class MatchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MatchAdapter
    private lateinit var matchViewModel: MatchViewModel
    private lateinit var matches: List<MatchModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        val tournamentId = intent.getLongExtra("tournament_id", -1)

        recyclerView = findViewById(R.id.match_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = MatchAdapter(this)
        recyclerView.adapter = adapter

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
        matchViewModel.getAllMatchModelsForTournament(tournamentId).observe(this, Observer<List<MatchModel>> {
            matches = it ?: emptyList()
            adapter.setMatches(matches)
        })

    }
}
