package me.jamestoohey.matchup.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.viewmodel.NewTeamViewModel

class AddBatchTeamsFragment: Fragment() {
    private lateinit var editText: EditText
    private lateinit var addTeamsButton: Button
    private lateinit var newTeamViewModel: NewTeamViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_batch_add, container, false)

        editText = view.findViewById(R.id.batch_teams_list)
        addTeamsButton = view.findViewById(R.id.add_batch_teams_button)

        newTeamViewModel = activity?.run {
            ViewModelProviders.of(this).get(NewTeamViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        addTeamsButton.setOnClickListener {
            val enteredText = editText.text.toString()
            if (enteredText != "") {
                val teamStringList: List<String> = enteredText.split("\n")
                teamStringList.forEach {
                    if (validTeamName(it)) {
                        newTeamViewModel.insert(Team(it, ""))
                    }
                }
                Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                activity?.finish() // make success message
            } else {
                Toast.makeText(activity, "No teams were entered!", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    private fun validTeamName(name: String): Boolean = name != ""


}