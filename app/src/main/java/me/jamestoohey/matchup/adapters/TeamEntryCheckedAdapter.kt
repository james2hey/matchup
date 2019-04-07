package me.jamestoohey.matchup.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team

class TeamEntryCheckedAdapter(private var context: Context) : BaseAdapter(), Filterable {
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                return if (constraint.isNullOrEmpty()) {
                    filteredData = dataSource
                    filterResults.values = filteredData
                    filterResults

                } else {
                    val filteredData = dataSource.filter { it.name.startsWith(constraint.toString(), true) }
                    filterResults.values = filteredData
                    filterResults
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = results?.values as List<Team>
                notifyDataSetChanged()
            }

        }
    }

    private var dataSource = emptyList<Team>()
    private var filteredData = emptyList<Team>()
    private var checkedTeams: Set<Team> = emptySet()
    //    private var stateChangeSet = emptySet<Team>()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var teamName: TextView
        lateinit var teamImage: ImageView
        lateinit var checked: CheckBox
    }

    fun setTeams(teams: List<Team>) {
        dataSource = teams
        filteredData = teams
        notifyDataSetChanged()
    }

    fun setCheckedTeams(teamsForTournament: List<Team>) {
        checkedTeams = teamsForTournament.toSet()
    }

    fun getCheckedTeams(): List<Team> = checkedTeams.toList()

    override fun getCount(): Int = filteredData.size

    override fun getItem(position: Int): Team = filteredData[position]

    override fun getItemId(position: Int): Long = filteredData[position].teamId

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

        checkBox.isFocusable = false
        checkBox.isChecked = checkedTeams.contains(team)

        checkBox.setOnClickListener {
            Log.d("CHECK", checkBox.isChecked.toString())

            val isChecked = checkBox.isChecked
            checkedTeams = if (isChecked) checkedTeams.plusElement(team) else checkedTeams.minusElement(team)
            notifyDataSetChanged()
        }


        imageView.setImageURI(null)
        imageView.setBackgroundResource(0)
        if (team.imagePath != null) {
            // TODO find if this needs to happen everywhere.
            val permittedUri =
                context.contentResolver.persistedUriPermissions.filter { it.uri.toString() == team.imagePath }.first()
                    .uri
            imageView.setImageURI(permittedUri)
        } else {
            imageView.setBackgroundResource(R.drawable.placeholder_team)
        }

        return view
    }


}
