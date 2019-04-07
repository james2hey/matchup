package me.jamestoohey.matchup.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import me.jamestoohey.matchup.data.AppDatabase
import me.jamestoohey.matchup.data.entity.Tournament
import me.jamestoohey.matchup.data.dao.TournamentDao

class TournamentRepository(val context: Context) {

    private val tournamentDao: TournamentDao = AppDatabase.getInstance(context).tournamentDao()

    fun getAllTournaments(): LiveData<List<Tournament>> {
        return tournamentDao.getAllTournaments()
    }

    fun getTournament(id: Long): Tournament {
        return tournamentDao.getTournament(id)
    }

    fun insert(tournament: Tournament): Long {
        val a = object : AsyncTask<Tournament, Void, Long>() {
            override fun doInBackground(vararg tournament: Tournament): Long {
                return tournamentDao.insert(tournament[0])
            }

        }
        return a.execute(tournament).get()
    }

}