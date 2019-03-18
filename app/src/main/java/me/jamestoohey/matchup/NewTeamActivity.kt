package me.jamestoohey.matchup

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import me.jamestoohey.matchup.team.TeamEntity
import me.jamestoohey.matchup.team.TeamViewModel

class NewTeamActivity : AppCompatActivity() {
    private lateinit var teamNameText: EditText
//    private lateinit var teamImageView: ImageView
    private lateinit var addButton: Button
    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_team)
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)

        teamNameText = findViewById(R.id.team_name_text)
//        teamImageView = findViewById(R.id.team_image_view)
        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val teamName = teamNameText.text.toString()
            if (teamName != "") {
                teamViewModel.insert(TeamEntity(teamName, ""))
            }
            finish()
        }
        teamViewModel.getAllTeams().observe(this, Observer<List<TeamEntity>> {
            Log.d("test", it.toString())

        })
    }
}
