package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.jamestoohey.matchup.data.entity.Match
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.MatchRepository
import me.jamestoohey.matchup.repository.TeamRepository

class MatchViewModel(application: Application): AndroidViewModel(application) {

    private val matchRepository: MatchRepository = MatchRepository(application)
    private val teamRepository: TeamRepository = TeamRepository(application)

    fun getMatchesForTournament(tournamentId: Long): LiveData<List<Match>> = matchRepository.getAllMatchesForTournament(tournamentId)

    fun getTeamById(teamId: Long): LiveData<Team?> = teamRepository.getTeamById(teamId)

    fun getMatchById(id: Long): LiveData<Match> = matchRepository.getMatchById(id)

}