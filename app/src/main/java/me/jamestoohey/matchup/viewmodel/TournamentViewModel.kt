package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import me.jamestoohey.matchup.data.entity.Tournament
import me.jamestoohey.matchup.repository.TournamentRepository

class TournamentViewModel(application: Application): AndroidViewModel(application) {
    private val tournamentRepository: TournamentRepository =
        TournamentRepository(application)
    private val tournaments: LiveData<List<Tournament>>

    init {
        tournaments = tournamentRepository.getAllTournaments()
    }

    fun getAllTournaments(): LiveData<List<Tournament>> {
        return tournaments
    }

    fun insert(tournament: Tournament): Long {
        return tournamentRepository.insert(tournament)
    }

}