package me.jamestoohey.matchup.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.viewmodel.NewTeamViewModel

class AddTeamFragment: Fragment() {
    private lateinit var teamNameText: EditText
    private lateinit var teamImageView: ImageView
    private lateinit var addButton: Button
    private lateinit var newTeamViewModel: NewTeamViewModel
    private var teamId: Long = -1
    private var oldTeamName: String? = null
    private var oldTeamImage: String? = null
    private var teamImageURI: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_team, container, false)

        teamNameText = view.findViewById(R.id.team_name_text)
        teamImageView = view.findViewById(R.id.team_image_view)
        addButton = view.findViewById(R.id.add_button)

        newTeamViewModel = activity?.run {
            ViewModelProviders.of(this).get(NewTeamViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        if (oldTeamImage != null) {
            teamImageView.setImageURI(Uri.parse(oldTeamImage))
        }

        if (teamId != (-1).toLong()) {
            teamNameText.setText(oldTeamName)
        }

        setListeners()
        return view
    }

    fun setBundle(bundle: Bundle?) {
        if (bundle != null) {
            teamId = bundle.getLong("team_id", -1)
            oldTeamName = bundle.getString("team_name")
            oldTeamImage = bundle.getString("team_image")

            Log.d("TEAM", "$teamId  $oldTeamName")
        }
    }

    private fun setListeners() {
        addButton.setOnClickListener {
            val enteredTeamName = teamNameText.text.toString()
            if (enteredTeamName != "") {
                if (oldTeamName == null || teamId == (-1).toLong()) {
                    newTeamViewModel.insert(Team(enteredTeamName, teamImageURI))

                } else {
                    val team = Team(enteredTeamName, teamImageURI)
                    team.teamId = teamId
                    newTeamViewModel.update(team)
                }

                Toast.makeText(activity, context?.getString(R.string.success), Toast.LENGTH_LONG).show()
                activity?.finish()
            } else {
                Toast.makeText(activity, context?.getString(R.string.no_teams_entered), Toast.LENGTH_LONG).show()
            }
        }
        teamImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
                action = Intent.ACTION_OPEN_DOCUMENT
            }

            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            Log.d("ERROR", "there was an error uploading the image")
            return
        }
        val uri = data.data
        if (uri != null) {
            context?.contentResolver?.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            teamImageView.setImageURI(uri)
            teamImageURI = uri.toString()
        }

    }
}