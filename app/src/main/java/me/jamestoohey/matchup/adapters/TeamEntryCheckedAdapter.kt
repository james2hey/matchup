package me.jamestoohey.matchup.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team
import java.io.FileDescriptor

class TeamEntryCheckedAdapter(
    context: Context): BaseAdapter() {
    private var dataSource = emptyList<Team>()
    private var teamsToCheck = emptyList<Team>()
    private var stateChangeSet = emptySet<Team>()
    private var context = context

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var teamName: TextView
        lateinit var teamImage: ImageView
        lateinit var checked: CheckBox
    }

    fun setTeams(teams: List<Team>) {
        dataSource = teams
        notifyDataSetChanged()
    }

    fun getChangedTeams(): List<Team> = stateChangeSet.toList()

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Team = dataSource[position]

    override fun getItemId(position: Int): Long = dataSource[position].teamId

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_team_entry_checked, parent, false)

            holder = ViewHolder()
            holder.teamName = view.findViewById(R.id.list_item_team_name) as TextView
            holder.teamImage = view.findViewById(R.id.list_item_team_image) as ImageView
            holder.checked = view.findViewById(R.id.check_box) as CheckBox

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val teamName: TextView = holder.teamName
        val checkBox: CheckBox = holder.checked
        val imageView: ImageView = holder.teamImage

        val team = getItem(position)
        teamName.text = team.name
        checkBox.isChecked = teamsToCheck.contains(team)
        checkBox.isFocusable = false

        if (team.imagePath != null) {
            Log.d("URL", team.imagePath)

            val uri = Uri.parse(team.imagePath)
            imageView.setImageURI(uri)
        }


        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            stateChangeSet = if(stateChangeSet.contains(team)) stateChangeSet.minus(team) else stateChangeSet.plus(team)
        }

        return view
    }

    fun setCheckedTeams(teamsForTournament: List<Team>) {
        teamsToCheck = teamsForTournament
    }

}
