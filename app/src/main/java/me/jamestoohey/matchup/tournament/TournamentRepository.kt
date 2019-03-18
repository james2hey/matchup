package me.jamestoohey.matchup.tournament

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import me.jamestoohey.matchup.AppDatabase

class TournamentRepository(val context: Context) {

    private val tournamentDao: TournamentDao = AppDatabase.getInstance(context).tournamentDao()

    fun getAllTournaments(): LiveData<List<TournamentEntity>> {
        return tournamentDao.getAllTournaments()
    }

    fun getTournament(id: Long): TournamentEntity {
        return tournamentDao.getTournament(id)
    }

    fun insert(tournamentEntity: TournamentEntity): Long {
        val a = object : AsyncTask<TournamentEntity, Void, Long>() {
            override fun doInBackground(vararg tournament: TournamentEntity): Long {
                return tournamentDao.insert(tournament[0])
            }

        }
        return a.execute(tournamentEntity).get()
    }

}