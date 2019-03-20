package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.util.Log
import me.jamestoohey.matchup.MatchModel
import kotlin.math.log2
import me.jamestoohey.matchup.data.entity.Match
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.MatchRepository
import me.jamestoohey.matchup.repository.TeamRepository

class MatchViewModel(application: Application): AndroidViewModel(application) {

    private val matchRepository: MatchRepository = MatchRepository(application)
    private val teamRepository: TeamRepository = TeamRepository(application)
    private val app = application

    fun getMatchesForTournament(tournamentId: Long): LiveData<List<Match>> {
        return matchRepository.getAllMatchesForTournament(tournamentId)
    }

    fun getAllMatchModelsForTournament(tournamentId: Long): LiveData<List<MatchModel>> = matchRepository.getAllMatchModelsForTournament(tournamentId)

    fun generateMatchesForTournament(tournamentId: Long, teamList: List<Team>) {

        matchRepository.deleteMatchesForTournament(tournamentId)

        val numberOfTeams = teamList.size
        Log.d("NUMBER OF TEAMS", numberOfTeams.toString())
        // number of teams =  2 ^ ceil( log2(numberOfTeams) ) - 1
        val logOfTeams = log2(numberOfTeams.toDouble())
        val ceilOfTeams = Math.ceil(logOfTeams)
        val numberOfMatches = (Math.pow(2.0, ceilOfTeams) - 1).toInt()
        Log.d("Number of matches", numberOfMatches.toString())

        val shuffledTeams = teamList.shuffled()
        val initialMatches: List<List<Team>> = shuffledTeams.chunked(2)

        var matchNumber = 1
        initialMatches.forEach {
            if (it.size == 2) {
                val homeTeam = it[0]
                val awayTeam = it[1]
                matchRepository.insertMatch(Match(tournamentId, homeTeam.teamId, awayTeam.teamId, "Match ${matchNumber++}"))
            } else if (it.size == 1) {
                val homeTeam = it[0]
                matchRepository.insertMatch(Match(tournamentId, homeTeam.teamId, null, "Match ${matchNumber++}"))
            }
        }
        var numberOfFinals = numberOfMatches / 2

        var finalNumber = 1
        while (numberOfFinals > 0) {
            when(numberOfFinals) {
                1 -> matchRepository.insertMatch(Match(tournamentId, null, null, "Final"))
                in 2..3 -> {
                    if (finalNumber > 2) finalNumber = 1
                    matchRepository.insertMatch(Match(tournamentId, null, null, "Semi-final ${finalNumber++}"))
                }
                in 4..7 -> {
                    if (finalNumber > 4) finalNumber = 1
                    matchRepository.insertMatch(Match(tournamentId, null, null, "Quarter-final ${finalNumber++}"))
                }
                else -> {
                    matchRepository.insertMatch(Match(tournamentId, null, null, "Match ${finalNumber++}"))
                }
            }
            numberOfFinals--
        }

    }
}