package me.jamestoohey.matchup.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.database.DataSetObserver
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.SearchView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.adapters.TeamEntryCheckedAdapter
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.viewmodel.SelectTeamsViewModel

class SelectTeamsActivity : AppCompatActivity() {
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    private lateinit var searchView: SearchView
    private lateinit var listAdapter: TeamEntryCheckedAdapter
    private lateinit var selectTeamsViewModel: SelectTeamsViewModel
    private lateinit var listView: ListView
    private lateinit var selectedTeamsCount: MenuItem
    private var teamsForTournament: List<Team> = emptyList()
    private var allTeams: List<Team> = emptyList()
    private var tournamentId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_teams)

        okButton = findViewById(R.id.ok_button)
        cancelButton = findViewById(R.id.cancel_button)
        listView = findViewById(R.id.tournament_team_checked_list)
        searchView = findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                listAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                listAdapter.filter.filter(query)
                return false
            }

        })

        listAdapter = TeamEntryCheckedAdapter(this)
        listView.adapter = listAdapter
        tournamentId = intent.getLongExtra("tournament_id", -1)
        title = "Select Teams"
        setListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        selectTeamsViewModel = ViewModelProviders.of(this).get(SelectTeamsViewModel::class.java)

        selectTeamsViewModel.getTeamsForTournament(tournamentId).observe(this, Observer<List<Team>> {
            teamsForTournament = it ?: emptyList()
            listAdapter.setCheckedTeams(teamsForTournament)
        })

        selectTeamsViewModel.getAllTeams().observe(this, Observer<List<Team>> {
            allTeams = it ?: emptyList()
            listAdapter.setTeams(allTeams)
        })
    }

    private fun setListeners() {
        okButton.setOnClickListener {
            val checkedTeams = listAdapter.getCheckedTeams()
            selectTeamsViewModel.updateTeamsInTournament(teamsForTournament, checkedTeams, tournamentId)
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
        listView.setOnItemLongClickListener { _, _, _, id ->
            val team = allTeams.find { it.teamId == id}
            val intent = Intent(this, AddTeamsActivity::class.java)
            intent.putExtra("team_id", team?.teamId)
            intent.putExtra("team_name", team?.name)
            intent.putExtra("team_image", team?.imagePath)
            startActivity(intent)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_team_menu, menu)
        selectedTeamsCount = menu.findItem(R.id.number_of_teams_selected)
        selectedTeamsCount.title = "(${listAdapter.getCheckedTeams().size})"
        listAdapter.registerDataSetObserver(object: DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                selectedTeamsCount.title = "(${listAdapter.getCheckedTeams().size})"
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.add -> {
                val intent = Intent(this, AddTeamsActivity::class.java)
                startActivity(intent)
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}
