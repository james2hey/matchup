package me.jamestoohey.matchup.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.jamestoohey.matchup.MatchModel
import me.jamestoohey.matchup.viewholder.MatchViewHolder
import me.jamestoohey.matchup.R
import me.jamestoohey.matchup.data.entity.Match

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
        val holder = MatchViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: MatchViewHolder, i: Int) {
        holder.matchName.text = matches[i].matchName
        setTeamNames(holder, i)
//        if (holder.homeTeamScore != null) holder.homeTeamScore.text = matches[i].htScore
    }

    private fun setTeamNames(holder: MatchViewHolder, i: Int) {
        if (matches[i].htName == null && matches[i].atName == null) {
            holder.homeTeamName.text = context.getString(R.string.tbc)
            holder.awayTeamName.text = context.getString(R.string.tbc)
        } else if (matches[i].atName == null) {
            holder.homeTeamName.text = matches[i].htName
            holder.awayTeamName.text = context.getString(R.string.bye)
        } else {
            holder.homeTeamName.text = matches[i].htName
            holder.awayTeamName.text = matches[i].atName
        }
    }
}