package me.jamestoohey.matchup.tournament

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class TournamentViewModel(application: Application): AndroidViewModel(application) {
    private val tournamentRepository: TournamentRepository = TournamentRepository(application)
    private val tournaments: LiveData<List<TournamentEntity>>

    init {
        tournaments = tournamentRepository.getAllTournaments()
    }

    fun getAllTournaments(): LiveData<List<TournamentEntity>> {
        return tournaments
    }

    fun insert(tournamentEntity: TournamentEntity): Long {
        return tournamentRepository.insert(tournamentEntity)
    }

}