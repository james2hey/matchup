package me.jamestoohey.matchup.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import me.jamestoohey.matchup.models.MatchModel
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.adapters.MatchAdapter
import me.jamestoohey.matchup.data.entity.Match
import me.jamestoohey.matchup.viewmodel.MatchViewModel

class MatchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MatchAdapter
    private lateinit var matchViewModel: MatchViewModel
    private lateinit var matches: List<Match>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        val tournamentId = intent.getLongExtra("tournament_id", -1)
        val tournamentName = intent.getStringExtra("tournament_name")
        title = tournamentName

        recyclerView = findViewById(R.id.match_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = MatchAdapter(this)
        recyclerView.adapter = adapter

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
        matchViewModel.getMatchesForTournament(tournamentId).observe(this, Observer<List<Match>> {
            matches = it ?: emptyList()
            val matchModels = getMatchModels()
            adapter.setMatches(matchModels)
        })

    }

    private fun getMatchModels(): List<MatchModel> {
        val matchList: ArrayList<MatchModel> = ArrayList()
        matches.forEach {
            val htid = it.htid
            val atid = it.atid
            val hMatchId = it.htMatch
            val aMatchId = it.atMatch

            val match = MatchModel(
                it.toid,
                it.matchId,
                it.matchName,
                null,
                null,
                null,
                null,
                null,
                null
            )

            if (htid != null) {
                matchViewModel.getTeamById(htid).observe(this, Observer {team ->
                    if (htid == team?.teamId) {
                        adapter.updateMatchHomeTeam(match, team)
                    }
                })
            }

            if (atid != null) {
                matchViewModel.getTeamById(atid).observe(this, Observer {team ->
                    if (atid == team?.teamId) {
                        adapter.updateMatchAwayTeam(match, team)
                    }
                })
            }

            if (hMatchId != null) {
                matchViewModel.getMatchById(hMatchId).observe(this, Observer { homeMatch ->
                    if (hMatchId == homeMatch?.matchId) {
                        adapter.updateHomeMatch(match, homeMatch.matchId)
                    }
                })
            }

            if (aMatchId != null) {
                matchViewModel.getMatchById(aMatchId).observe(this, Observer { awayMatch ->
                    if (aMatchId == awayMatch?.matchId) {
                        adapter.updateAwayMatch(match, awayMatch.matchId)
                    }
                })
            }
            matchList.add(match)
        }
    return matchList
    }
}
