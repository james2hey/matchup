package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.TeamRepository

class SelectTeamsViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository = TeamRepository(application)

    fun getAllTeams(): LiveData<List<Team>> = teamRepository.getAllTeams()

    fun getTeamsForTournament(tournamentId: Long): LiveData<List<Team>> = teamRepository.getTeamsForTournament(tournamentId)

    fun insert(team: Team) = teamRepository.insert(team)

    fun updateTeamsInTournament(teamsForTournament: List<Team>, changedTeams: List<Team>, tournamentId: Long) {
        changedTeams.forEach {
            if (teamsForTournament.contains(it)) teamRepository.removeTeamFromTournament(it, tournamentId) else {
                teamRepository.insertTeamToTournament(it, tournamentId)
            }
        }
    }

}