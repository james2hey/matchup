package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.jamestoohey.matchup.data.entity.Match
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.MatchRepository
import me.jamestoohey.matchup.repository.TeamRepository
import kotlin.math.log2

class TournamentTeamsViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository = TeamRepository(application)
    private val matchRepository: MatchRepository = MatchRepository(application)

    fun getTeamsForTournament(tournamentId: Long): LiveData<List<Team>> = teamRepository.getTeamsForTournament(tournamentId)

    fun generateMatchesForTournament(tournamentId: Long, teamList: List<Team>) {

        matchRepository.deleteMatchesForTournament(tournamentId)
        val numberOfTeams = teamList.size
        val numberOfMatches = calculateNumberOfMatches(numberOfTeams)
        val matchIdList = createInitialMatches(numberOfMatches, teamList, tournamentId)
        createFinals(numberOfMatches, tournamentId, matchIdList)
    }

    /**
     * number of teams =  2 ^ ceil(log2(numberOfTeams)) - 1
     */
    private fun calculateNumberOfMatches(numberOfTeams: Int): Int {
        val logOfTeams = log2(numberOfTeams.toDouble())
        val ceilOfTeams = Math.ceil(logOfTeams)
        return (Math.pow(2.0, ceilOfTeams) - 1).toInt()
    }

    private fun createInitialMatches(numberOfMatches: Int, teamList: List<Team>, tournamentId: Long): ArrayList<Long> {
        val shuffledTeams = teamList.shuffled()
        val initialMatches: ArrayList<List<Team>> = shuffledTeams.chunked(2) as ArrayList<List<Team>>

        val numberOfInitialMatches = (numberOfMatches + 1) / 2
        while(initialMatches.size < numberOfInitialMatches) {
            initialMatches.add(emptyList())
        }

        val matchIdList: ArrayList<Long> = ArrayList()
        var matchNumber = 1
        initialMatches.forEach {
            val match = when(it.size) {
                2 -> Match(tournamentId, it[0].teamId, it[1].teamId, "Match ${matchNumber++}", null, null)
                1 -> Match(tournamentId, it[0].teamId, null, "Match ${matchNumber++}", null, null)
                0 -> Match(tournamentId, null, null, "Match ${matchNumber++}", null, null)
                else -> throw Error("Invalid initial match")
            }
            val id = matchRepository.insertMatch(match)
            matchIdList.add(id)
        }
        return matchIdList
    }

    private fun createFinals(numberOfMatches: Int, tournamentId: Long, matchIdList: ArrayList<Long>) {
        var numberOfFinals = numberOfMatches / 2
        var finalNumber = 1
        var matchIndex = 0

        while (numberOfFinals > 0) {
            val match = when(numberOfFinals) {
                1 -> Match(tournamentId, null, null, "Final", matchIdList[matchIndex++], matchIdList[matchIndex++])
                in 2..3 -> {
                    if (finalNumber > 2) finalNumber = 1
                    Match(tournamentId, null, null, "Semi-final ${finalNumber++}", matchIdList[matchIndex++], matchIdList[matchIndex++])
                }
                in 4..7 -> {
                    if (finalNumber > 4) finalNumber = 1
                    Match(tournamentId, null, null, "Quarter-final ${finalNumber++}", matchIdList[matchIndex++], matchIdList[matchIndex++])
                }
                else -> {
                    Match(tournamentId, null, null, "Match ${finalNumber++}", matchIdList[matchIndex++], matchIdList[matchIndex++])
                }
            }
            val id = matchRepository.insertMatch(match)
            matchIdList.add(id)
            numberOfFinals--
        }
    }

}