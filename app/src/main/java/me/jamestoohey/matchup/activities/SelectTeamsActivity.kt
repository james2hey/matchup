package me.jamestoohey.matchup.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.viewmodel.SelectTeamsViewModel
import me.jamestoohey.matchup.adapters.TeamEntryCheckedAdapter
import me.jamestoohey.matchup.data.entity.Team

class SelectTeamsActivity : AppCompatActivity() {
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    private lateinit var createTeamButton: Button
    private lateinit var listAdapter: TeamEntryCheckedAdapter
    private lateinit var selectTeamsViewModel: SelectTeamsViewModel
    private lateinit var listView: ListView
    private lateinit var teamsForTournament: List<Team>
    private var tournamentId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_teams)

        okButton = findViewById(R.id.ok_button)
        cancelButton = findViewById(R.id.cancel_button)
        createTeamButton = findViewById(R.id.create_team_button)
        listView = findViewById(R.id.tournament_team_checked_list)

        listAdapter = TeamEntryCheckedAdapter(this)
        listView.adapter = listAdapter
        tournamentId = intent.getLongExtra("tournament_id", -1)

        setButtonListeners()

        observeViewModel()
    }

    private fun observeViewModel() {
        selectTeamsViewModel = ViewModelProviders.of(this).get(SelectTeamsViewModel::class.java)

        selectTeamsViewModel.getTeamsForTournament(tournamentId).observe(this, Observer<List<Team>> {
            teamsForTournament = it ?: emptyList()
            listAdapter.setCheckedTeams(teamsForTournament)
        })

        selectTeamsViewModel.getAllTeams().observe(this, Observer<List<Team>> {
            val allTeams = it ?: emptyList()
            listAdapter.setTeams(allTeams.sortedBy { team -> !teamsForTournament.contains(team) })
        })
    }

    private fun setButtonListeners() {
        okButton.setOnClickListener {
            val changedTeams = listAdapter.getChangedTeams()
            selectTeamsViewModel.updateTeamsInTournament(teamsForTournament, changedTeams, tournamentId)
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
        createTeamButton.setOnClickListener {
            val intent = Intent(this, NewTeamActivity::class.java)
            startActivity(intent)
        }
    }
}
