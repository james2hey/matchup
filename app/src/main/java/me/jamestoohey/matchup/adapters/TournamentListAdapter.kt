package me.jamestoohey.matchup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Tournament

class TournamentListAdapter(
    context: Context
): BaseAdapter() {
    private var dataSource = emptyList<Tournament>()

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private class ViewHolder {
        lateinit var tournamentName: TextView
    }

    fun setTournaments(tournamentList: List<Tournament>) {
        dataSource = tournamentList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Tournament {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.tournament_list_item, parent, false)

            holder = ViewHolder()
            holder.tournamentName = view.findViewById(R.id.tournament_title) as TextView

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val tournamentName: TextView = holder.tournamentName
        val tournament = getItem(position) as Tournament
        tournamentName.text = tournament.title
        return view
    }

}