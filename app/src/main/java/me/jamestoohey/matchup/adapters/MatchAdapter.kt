package me.jamestoohey.matchup.adapters

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
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

        val homeTeamScore: EditText = view.findViewById(R.id.home_team_score)
        val awayTeamScore: EditText = view.findViewById(R.id.away_team_score)
        val holder = MatchViewHolder(view)

//        view.setOnLongClickListener {
//            val selectedIndex = holder.adapterPosition
//            notifyItemChanged(selectedIndex)
//            Log.d("CLICKED", selectedIndex.toString())
//            true
//        }
        return holder
    }

    override fun onBindViewHolder(holder: MatchViewHolder, i: Int) {
        holder.matchName.text = matches[i].matchName
        setTeamNames(holder, i)
        setTeamImages(holder, i)
    }

    private fun setTeamImages(holder: MatchViewHolder, i: Int) {
        val match = matches[i]
        val homeTeam = match.homeTeam
        val awayTeam = match.awayTeam
        clearImages(holder)

        if (homeTeam?.imagePath != null) {
            val uri = Uri.parse(homeTeam.imagePath)
            holder.homeTeamImage.setImageURI(uri)
        } else {
            holder.homeTeamImage.setBackgroundResource(R.drawable.placeholder_team)
        }

        if (awayTeam?.imagePath != null) {
            val uri = Uri.parse(awayTeam.imagePath)
            holder.awayTeamImage.setImageURI(uri)
        } else {
            holder.awayTeamImage.setBackgroundResource(R.drawable.placeholder_team)
        }

        holder.homeTeamImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.match_animation))
        holder.awayTeamImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.match_animation))
    }

    private fun clearImages(holder: MatchViewHolder) {
        holder.homeTeamImage.setImageURI(null)
        holder.awayTeamImage.setImageURI(null)
        holder.homeTeamImage.setBackgroundResource(0)
        holder.awayTeamImage.setBackgroundResource(0)
    }

    private fun setTeamNames(holder: MatchViewHolder, i: Int) {
        if (matches[i].homeMatch != null && matches[i].awayMatch != null) {

            holder.homeTeamName.text = "W ${matches[i].homeMatch?.matchName}"
            holder.awayTeamName.text = "W ${matches[i].awayMatch?.matchName}"

        } else if (matches[i].homeTeam == null && matches[i].awayTeam == null) {
            holder.homeTeamName.text = context.getText(R.string.no_team)
            holder.awayTeamName.text = context.getText(R.string.no_team)
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