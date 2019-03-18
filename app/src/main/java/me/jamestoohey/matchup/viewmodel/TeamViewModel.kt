package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.TeamRepository

class TeamViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository =
        TeamRepository(application)
    private val teams: LiveData<List<Team>>

    init {
        teams = teamRepository.getAllTeams()
    }

    fun getAllTeams(): LiveData<List<Team>> {
        return teams
    }

    fun getTeamsForTournament(tournamentId: Long): LiveData<List<Team>> {
        return teamRepository.getTeamsForTournament(tournamentId)
    }

    fun insert(team: Team) {
        teamRepository.insert(team)
    }

}