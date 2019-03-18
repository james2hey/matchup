package me.jamestoohey.matchup.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Button
import android.widget.ListView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.adapters.TeamEntryAdapter
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.viewmodel.TeamViewModel

class TournamentTeamsActivity : AppCompatActivity() {

    private lateinit var generateTournamentButton: Button
    private lateinit var addTeamsButton: FloatingActionButton
    private lateinit var listView: ListView
    private lateinit var listAdapter: TeamEntryAdapter
    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_teams)
        title = intent.getStringExtra("tournament_name")
        val tournamentId = intent.getLongExtra("tournament_id", -1)

        listView = findViewById(R.id.tournament_team_list)
        listAdapter = TeamEntryAdapter(this)
        listView.adapter = listAdapter

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)
        teamViewModel.getTeamsForTournament(tournamentId).observe(this, Observer<List<Team>> {
            if (it != null) {
                listAdapter.setTeams(it.toList())
            } else {
                listAdapter.setTeams(emptyList())
            }

        })

        generateTournamentButton = findViewById(R.id.generate_tournament)
        addTeamsButton = findViewById(R.id.add_teams_button)


        addTeamsButton.setOnClickListener {
            val intent = Intent(this, SelectTeamsActivity::class.java)
            intent.putExtra("tournament_id", tournamentId)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
    }
}
