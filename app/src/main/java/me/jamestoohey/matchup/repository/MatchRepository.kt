package me.jamestoohey.matchup.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import me.jamestoohey.matchup.data.AppDatabase
import me.jamestoohey.matchup.data.dao.MatchDao
import me.jamestoohey.matchup.data.entity.Match

class MatchRepository(val context: Context) {

    private val matchDao: MatchDao = AppDatabase.getInstance(context).matchDao()

    fun getAllMatchesForTournament(tournamentId: Long): LiveData<List<Match>> {
        return matchDao.getAllMatchesForTournament(tournamentId)
    }

    fun insertMatch(match: Match): Long = MatchInsertAsyncTask(matchDao).execute(match).get()

    fun getMatchById(id: Long): LiveData<Match> = matchDao.getMatchById(id)

    fun deleteMatches(matches: List<Match>) {
        MatchDeleteAsyncTask(matchDao).execute(matches)
    }

    fun deleteMatchesForTournament(tournamentId: Long) {
        MatchDeleteForTournamentAsyncTask(matchDao).execute(tournamentId)
    }

}

class MatchInsertAsyncTask(private val matchDao: MatchDao) : AsyncTask<Match, Void, Long>() {
    override fun doInBackground(vararg match: Match): Long {
        return matchDao.insert(match[0])
    }
}

class MatchDeleteAsyncTask(private val matchDao: MatchDao) : AsyncTask<List<Match>, Void, Unit>() {
    override fun doInBackground(vararg matches: List<Match>) {
        matches[0].forEach {
            matchDao.delete(it)
        }
    }
}

class MatchDeleteForTournamentAsyncTask(private val matchDao: MatchDao) : AsyncTask<Long, Void, Unit>() {
    override fun doInBackground(vararg tournamentId: Long?) {
        matchDao.deleteMatchesForTournament(tournamentId[0]!!)
    }
}