package me.jamestoohey.matchup.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.match_item.view.*
import me.jamestoohey.matchup.R

class MatchViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val matchName: TextView = view.findViewById(R.id.match_name)
//    val homeTeamImage: Image = view.findViewById(R.id.home_team_image)
    val homeTeamName: TextView = view.findViewById(R.id.home_team_name)
    val homeTeamScore: TextView = view.findViewById(R.id.home_team_score)
//    val awayTeamImage: Image = view.findViewById(R.id.away_team_image)
    val awayTeamName: TextView = view.findViewById(R.id.away_team_name)
    val awayTeamScore: TextView = view.findViewById(R.id.away_team_score)

}