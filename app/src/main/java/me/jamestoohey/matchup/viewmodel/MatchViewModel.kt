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

}