package me.jamestoohey.matchup

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import me.jamestoohey.matchup.team.TeamEntity
import me.jamestoohey.matchup.team.TeamViewModel

class SelectTournamentTeamsActivity : AppCompatActivity() {

    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    private lateinit var createTeamButton: Button
    private lateinit var listAdapter: TeamEntryCheckedAdapter
    private lateinit var selectTournamentTeamsViewModel: SelectTournamentTeamsViewModel
    private lateinit var listView: ListView
    private lateinit var teamsForTournament: List<TeamEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_teams)
        listAdapter = TeamEntryCheckedAdapter(this)
        listView = findViewById(R.id.tournament_team_checked_list)
        listView.adapter = listAdapter
        val tournamentId = intent.getLongExtra("tournament_id", -1)

        selectTournamentTeamsViewModel = ViewModelProviders.of(this).get(SelectTournamentTeamsViewModel::class.java)
//        selectTournamentTeamsViewModel.setTournamentId(tournamentId)
        selectTournamentTeamsViewModel.getAllTeams().observe(this, Observer<List<TeamEntity>> {
            if (it != null) listAdapter.setTeams(it) else {
                listAdapter.setTeams(emptyList())
            }
        })

        selectTournamentTeamsViewModel.getTeamsForTournament(tournamentId).observe(this, Observer<List<TeamEntity>> {
            teamsForTournament = it ?: emptyList()
        })

        okButton = findViewById(R.id.ok_button)
        cancelButton = findViewById(R.id.cancel_button)
        createTeamButton = findViewById(R.id.create_team_button)

        okButton.setOnClickListener {
            val changedTeams = listAdapter.getChangedTeams()
            Log.d("CHANGE TEAMS", changedTeams.size.toString())
            selectTournamentTeamsViewModel.updateTeamsInTournament(teamsForTournament, changedTeams, tournamentId)
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

    override fun onResume() {
        super.onResume()

        listAdapter.notifyDataSetChanged()
    }
}
