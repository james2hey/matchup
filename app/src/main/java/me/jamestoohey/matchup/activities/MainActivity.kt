package me.jamestoohey.matchup.activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.arch.lifecycle.ViewModelProviders
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.adapters.TournamentListAdapter
import me.jamestoohey.matchup.data.entity.Tournament
import me.jamestoohey.matchup.viewmodel.TournamentViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var tournamentName: EditText
    private lateinit var createButton: Button
    private lateinit var listView: ListView
    private lateinit var tournamentViewModel: TournamentViewModel
    private lateinit var listAdapter: TournamentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tournamentName = findViewById(R.id.tournament_name)
        createButton = findViewById(R.id.create_button)
        listView = findViewById(R.id.tournament_list_view)

        listAdapter = TournamentListAdapter(this)
        listView.adapter = listAdapter

        observeViewModel()
        setListeners()
    }

    private fun observeViewModel() {
        tournamentViewModel = ViewModelProviders.of(this).get(TournamentViewModel::class.java)
        tournamentViewModel.getAllTournaments().observe(this, Observer<List<Tournament>> {
            if (it != null) {
                listAdapter.setTournaments(it.toList())
            } else {
                listAdapter.setTournaments(emptyList())
            }
        })
    }

    private fun setListeners() {
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTournament = listAdapter.getItem(position)
            val intent = Intent(this, TournamentTeamsActivity::class.java).apply {
                type = "image/*"
                action = Intent.ACTION_OPEN_DOCUMENT
            }
            intent.putExtra("tournament_name", selectedTournament.title)
            intent.putExtra("tournament_id", selectedTournament.tournamentId)
            startActivity(intent)
        }

        createButton.setOnClickListener {
            val tournamentId = tournamentViewModel.insert(Tournament(tournamentName.text.toString(), false))
            val intent = Intent(this, TournamentTeamsActivity::class.java)
            intent.putExtra("tournament_name", tournamentName.text.toString())
            intent.putExtra("tournament_id", tournamentId)
            startActivity(intent)
        }
    }
}
