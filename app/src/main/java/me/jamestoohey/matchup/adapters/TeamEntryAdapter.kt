package me.jamestoohey.matchup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team

class TeamEntryAdapter(
    context: Context): BaseAdapter() {
    private var dataSource = emptyList<Team>()

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var teamName: TextView
//        lateinit var teamImage: ImageView
    }

    fun setTeams(teams: List<Team>) {
        dataSource = teams
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_team_entry, parent, false)

            holder = ViewHolder()
            holder.teamName = view.findViewById(R.id.list_item_team_name) as TextView

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val teamName: TextView = holder.teamName
        val team = getItem(position) as Team
        teamName.text = team.name

        view.setOnClickListener{

        }
        return view
    }

}