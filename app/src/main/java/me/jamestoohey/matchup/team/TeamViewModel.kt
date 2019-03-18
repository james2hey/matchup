package me.jamestoohey.matchup.team

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class TeamViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository = TeamRepository(application)
    private val teams: LiveData<List<TeamEntity>>

    init {
        teams = teamRepository.getAllTeams()
    }

    fun getAllTeams(): LiveData<List<TeamEntity>> {
        return teams
    }

    fun getTeamsForTournament(tournamentId: Long): LiveData<List<TeamEntity>> {
        return teamRepository.getTeamsForTournament(tournamentId)
    }

    fun insert(teamEntity: TeamEntity) {
        teamRepository.insert(teamEntity)
    }

}