package me.jamestoohey.matchup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import me.jamestoohey.matchup.team.TeamEntity

class TeamEntryCheckedAdapter(
    context: Context): BaseAdapter() {
    private var dataSource = emptyList<TeamEntity>()
    private var stateChangeStack = emptySet<TeamEntity>()

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var teamName: TextView
//        lateinit var teamImage: ImageView
        lateinit var checked: CheckBox
    }

    fun setTeams(teams: List<TeamEntity>) {
        dataSource = teams
        notifyDataSetChanged()
    }

    fun getChangedTeams(): List<TeamEntity> {
        return stateChangeStack.toList()
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
            view = inflater.inflate(R.layout.list_item_team_entry_checked, parent, false)

            holder = ViewHolder()
            holder.teamName = view.findViewById(R.id.list_item_team_name) as TextView
            holder.checked = view.findViewById(R.id.check_box) as CheckBox

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val teamName: TextView = holder.teamName
        val checkBox: CheckBox = holder.checked

//        val teamName: EditText = rowView.findViewById(R.id.list_item_team_name)
        val team = getItem(position) as TeamEntity
        teamName.text = team.name


        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(stateChangeStack.contains(team)) stateChangeStack = stateChangeStack.minus(team) else  stateChangeStack = stateChangeStack.plus(team)
        }

        return view
    }

}
