package me.jamestoohey.matchup.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.database.DataSetObserver
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.adapters.TeamEntryAdapter
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.viewmodel.TournamentTeamsViewModel

class TournamentTeamsActivity : AppCompatActivity() {

    private lateinit var generateTournamentButton: Button
    private lateinit var addTeamsButton: FloatingActionButton
    private lateinit var listView: ListView
    private lateinit var listAdapter: TeamEntryAdapter
    private lateinit var teamsCount: MenuItem
    private lateinit var tournamentTeamsViewModel: TournamentTeamsViewModel
    private lateinit var teamsInTournament: List<Team>
    private var tournamentId: Long = -1
    private var tournamentName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament_teams)

        listView = findViewById(R.id.tournament_team_list)
        generateTournamentButton = findViewById(R.id.generate_tournament)
        addTeamsButton = findViewById(R.id.add_teams_button)

        tournamentId = intent.getLongExtra("tournament_id", -1)
        tournamentName = intent.getStringExtra("tournament_name")
        title = tournamentName

        listAdapter = TeamEntryAdapter(this)
        listView.adapter = listAdapter

        observeViewModel()
        setButtonListeners()
    }

    private fun observeViewModel() {
        tournamentTeamsViewModel = ViewModelProviders.of(this).get(TournamentTeamsViewModel::class.java)
        tournamentTeamsViewModel.getTeamsForTournament(tournamentId).observe(this, Observer<List<Team>> {
            teamsInTournament = it ?: emptyList()
            listAdapter.setTeams(teamsInTournament)
        })
    }

    private fun setButtonListeners() {
        addTeamsButton.setOnClickListener {
            val intent = Intent(this, SelectTeamsActivity::class.java)
            intent.putExtra("tournament_id", tournamentId)

            startActivity(intent)
        }

        generateTournamentButton.setOnClickListener {
            tournamentTeamsViewModel.generateMatchesForTournament(tournamentId, teamsInTournament)
            val intent = Intent(this, MatchActivity::class.java)
            intent.putExtra("tournament_name", tournamentName)
            intent.putExtra("tournament_id", tournamentId)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.tournament_teams_menu, menu)
        teamsCount = menu.findItem(R.id.number_of_teams_in_tournament)
        teamsCount.title = "(${listAdapter.count})"
        listAdapter.registerDataSetObserver(object: DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                teamsCount.title = "(${listAdapter.count})"
            }
        })
        return true
    }
}
