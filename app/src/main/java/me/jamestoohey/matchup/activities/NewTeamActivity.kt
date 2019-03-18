package me.jamestoohey.matchup.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.viewmodel.TeamViewModel

class NewTeamActivity : AppCompatActivity() {
    private lateinit var teamNameText: EditText
    private lateinit var teamImageView: View
    private lateinit var addButton: Button
    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_team)
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)

        teamNameText = findViewById(R.id.team_name_text)
        teamImageView = findViewById(R.id.team_image_view)
        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val teamName = teamNameText.text.toString()
            if (teamName != "") {
                teamViewModel.insert(Team(teamName, ""))
            }
            finish()
        }
        teamViewModel.getAllTeams().observe(this, Observer<List<Team>> {
            Log.d("test", it.toString())

        })
    }
}
