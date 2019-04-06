package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.TeamRepository

class SelectTeamsViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository = TeamRepository(application)

    fun getAllTeams(): LiveData<List<Team>> = teamRepository.getAllTeams()

    fun getTeamsForTournament(tournamentId: Long): LiveData<List<Team>> = teamRepository.getTeamsForTournament(tournamentId)

    fun insert(team: Team) = teamRepository.insert(team)

    fun updateTeamsInTournament(prevTeams: List<Team>, nextTeams: List<Team>, tournamentId: Long) {
        val prevSet: Set<Team> = prevTeams.toSet()
        val nextSet: Set<Team> = nextTeams.toSet()
        val unionSet = prevSet.union(nextSet)
        val intersectionSet = prevSet.intersect(nextSet)
        val newTeamsInTournament = unionSet.minus(intersectionSet)

        newTeamsInTournament.forEach{
            if (prevTeams.contains(it)) teamRepository.removeTeamFromTournament(it, tournamentId) else {
                teamRepository.insertTeamToTournament(it, tournamentId)
            }
        }

    }

}