package me.jamestoohey.matchup

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.arch.lifecycle.ViewModelProviders
import me.jamestoohey.matchup.team.TeamEntity
import me.jamestoohey.matchup.team.TeamViewModel
import me.jamestoohey.matchup.tournament.TournamentEntity
import me.jamestoohey.matchup.tournament.TournamentViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var tournamentName: EditText
    private lateinit var groupStages: Switch
    private lateinit var nextButton: Button
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var tournamentViewModel: TournamentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)
        teamViewModel.getAllTeams().observe(this, Observer<List<TeamEntity>> {

        })
        tournamentViewModel = ViewModelProviders.of(this).get(TournamentViewModel::class.java)
        val tournamentId = tournamentViewModel.insert(TournamentEntity("World Cup", true))

        tournamentName = findViewById(R.id.tournament_name)
        groupStages = findViewById(R.id.group_stages)
        nextButton = findViewById(R.id.next_button)

        nextButton.setOnClickListener {
            val intent = Intent(this, TournamentTeamsActivity::class.java)
            intent.putExtra("tournament_name", tournamentName.text.toString())
            intent.putExtra("tournament_id", tournamentId)
            startActivity(intent)
        }
    }

}
