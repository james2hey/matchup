package me.jamestoohey.matchup.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import me.jamestoohey.matchup.models.MatchModel
import me.jamestoohey.matchup.viewholder.MatchViewHolder
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Team

class MatchAdapter(val context: Context): RecyclerView.Adapter<MatchViewHolder>() {
    private var matches: List<MatchModel> = emptyList()

    fun setMatches(matchesList: List<MatchModel>) {
        matches = matchesList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = matches.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MatchViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.match_item, parent, false)

        val homeTeamScore: TextView = view.findViewById(R.id.home_team_score)
        val awayTeamScoer: TextView = view.findViewById(R.id.away_team_score)
        val holder = MatchViewHolder(view)

        view.setOnLongClickListener {
            // somehow set the score
            val selectedIndex = holder.adapterPosition
            notifyItemChanged(selectedIndex)
            Log.d("CLICKED", selectedIndex.toString())
            true
        }
        return holder
    }

    override fun onBindViewHolder(holder: MatchViewHolder, i: Int) {
        holder.matchName.text = matches[i].matchName
        setTeamNames(holder, i)
    }

    private fun setTeamNames(holder: MatchViewHolder, i: Int) {
        if (matches[i].homeMatch != null && matches[i].awayMatch != null) {

            holder.homeTeamName.text = "W ${matches[i].homeMatch?.matchName}"
            holder.awayTeamName.text = "W ${matches[i].awayMatch?.matchName}"

        } else if (matches[i].homeTeam == null && matches[i].awayTeam == null) {
            holder.homeTeamName.text = "No Team"
            holder.awayTeamName.text = "No Team"
        } else if (matches[i].awayTeam == null) {
            holder.homeTeamName.text = matches[i].homeTeam?.name
            holder.awayTeamName.text = context.getString(R.string.bye)
        } else {
            holder.homeTeamName.text = matches[i].homeTeam?.name
            holder.awayTeamName.text = matches[i].awayTeam?.name
        }
    }

    fun updateMatchHomeTeam(match: MatchModel, team: Team?) {
        val i = matches.indexOf(match)
        if (i != -1) {
            matches[i].homeTeam = team
        }
        notifyDataSetChanged()
    }

    fun updateMatchAwayTeam(match: MatchModel, team: Team?) {
        val i = matches.indexOf(match)
        if (i != -1) {
            matches[i].awayTeam = team
        }
        notifyDataSetChanged()
    }

    fun updateHomeMatch(match: MatchModel, homeMatchId: Long?) {
        val i = matches.indexOf(match)
        if (i != -1) {
            val matchToMap = matches.filter { it.matchId == homeMatchId }
            if (matchToMap.isNotEmpty()) matches[i].homeMatch = matchToMap[0]
        }
        notifyDataSetChanged()
    }

    fun updateAwayMatch(match: MatchModel, awayMatchId: Long?) {
        val i = matches.indexOf(match)
        if (i != -1) {
            val matchToMap = matches.filter { it.matchId == awayMatchId }
            if (matchToMap.isNotEmpty()) matches[i].awayMatch = matchToMap[0]
        }
        notifyDataSetChanged()
    }
}