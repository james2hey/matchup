package me.jamestoohey.matchup.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ImageView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team

class TeamEntryAdapter(
    context: Context): BaseAdapter() {
    private var dataSource = emptyList<Team>()

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var teamName: TextView
        lateinit var teamImage: ImageView
    }

    fun setTeams(teams: List<Team>) {
        dataSource = teams
        notifyDataSetChanged()
    }

    override fun getCount(): Int = dataSource.size


    override fun getItem(position: Int): Team = dataSource[position]

    override fun getItemId(position: Int): Long = dataSource[position].teamId

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_team_entry, parent, false)

            holder = ViewHolder()
            holder.teamName = view.findViewById(R.id.list_item_team_name) as TextView
            holder.teamImage = view.findViewById(R.id.list_item_team_image) as ImageView
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val teamName: TextView = holder.teamName
        val imageView = holder.teamImage

        val team = getItem(position)
        teamName.text = team.name

        imageView.setImageURI(null)
        imageView.setBackgroundResource(0)
        if (team.imagePath != null) {
            val uri = Uri.parse(team.imagePath)
            imageView.setImageURI(uri)
        } else {
            imageView.setBackgroundResource(R.drawable.placeholder_team)
        }

        return view
    }

}
