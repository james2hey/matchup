package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.TeamRepository

class SelectTournamentTeamsViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository =
        TeamRepository(application)
    private val allTeams: LiveData<List<Team>>
//    private var tournamentId: Long = -1

    init {
        allTeams = teamRepository.getAllTeams()
    }

//    fun setTournamentId(id: Long) {
//        tournamentId = id
//        teamsForTournament = getTeamsForTournament()
//    }

    fun getAllTeams(): LiveData<List<Team>> {
        return allTeams
    }

    fun getTeamsForTournament(tournamentId: Long): LiveData<List<Team>> {
        return teamRepository.getTeamsForTournament(tournamentId)
    }

    fun insert(team: Team) {
        teamRepository.insert(team)
    }

    fun updateTeamsInTournament(teamsForTournament: List<Team>, changedTeams: List<Team>, tournamentId: Long) {

        changedTeams.forEach {
            if (teamsForTournament.contains(it)) {
                // delete
                teamRepository.removeTeamFromTournament(it, tournamentId)
            } else {
                teamRepository.insertTeamToTournament(it, tournamentId)
            }
            // if team is in teamsForTournament
            // Delete the team from TournamentTeamsJoin
            // else
            // Insert the team into TournamentTeamsJoin
        }
    }

}