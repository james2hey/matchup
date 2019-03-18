package me.jamestoohey.matchup

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.util.Log
import me.jamestoohey.matchup.team.TeamEntity
import me.jamestoohey.matchup.team.TeamRepository

class SelectTournamentTeamsViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository = TeamRepository(application)
    private val allTeams: LiveData<List<TeamEntity>>
//    private var tournamentId: Long = -1

    init {
        allTeams = teamRepository.getAllTeams()
    }

//    fun setTournamentId(id: Long) {
//        tournamentId = id
//        teamsForTournament = getTeamsForTournament()
//    }

    fun getAllTeams(): LiveData<List<TeamEntity>> {
        return allTeams
    }

    fun getTeamsForTournament(tournamentId: Long): LiveData<List<TeamEntity>> {
        return teamRepository.getTeamsForTournament(tournamentId)
    }

    fun insert(teamEntity: TeamEntity) {
        teamRepository.insert(teamEntity)
    }

    fun updateTeamsInTournament(teamsForTournament: List<TeamEntity>, changedTeams: List<TeamEntity>, tournamentId: Long) {

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