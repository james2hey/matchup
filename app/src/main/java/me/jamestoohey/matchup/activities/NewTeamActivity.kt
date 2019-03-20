package me.jamestoohey.matchup.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.viewmodel.NewTeamViewModel

class NewTeamActivity : AppCompatActivity() {
    private lateinit var teamNameText: EditText
    private lateinit var teamImageView: View
    private lateinit var addButton: Button
    private lateinit var newTeamViewModel: NewTeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_team)
        newTeamViewModel = ViewModelProviders.of(this).get(NewTeamViewModel::class.java)

        teamNameText = findViewById(R.id.team_name_text)
        teamImageView = findViewById(R.id.team_image_view)
        addButton = findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val teamName = teamNameText.text.toString()
            if (teamName != "") {
                val id = newTeamViewModel.insert(Team(teamName, ""))
            }
            finish()
        }
    }
}
