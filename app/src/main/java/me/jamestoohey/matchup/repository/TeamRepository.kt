package me.jamestoohey.matchup.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import me.jamestoohey.matchup.data.AppDatabase
import me.jamestoohey.matchup.data.entity.TournamentTeamJoin
import me.jamestoohey.matchup.data.dao.TournamentTeamJoinDao
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.data.dao.TeamDao

class TeamRepository(val context: Context) {

    private val teamDao: TeamDao = AppDatabase.getInstance(context).teamDao()
    private val tournamentTeamJoinDao = AppDatabase.getInstance(context).tournamentTeamJoinDao()

    fun getAllTeams(): LiveData<List<Team>> = teamDao.getAllTeams()

    fun insert(team: Team): Long = TeamInsertAsyncTask(teamDao).execute(team).get()

    fun getTeamsForTournament(id: Long): LiveData<List<Team>> = tournamentTeamJoinDao.getTeamsForTournament(id)

    fun insertTeamToTournament(team: Team, tournamentId: Long) {
        val tournamentTeamJoin = TournamentTeamJoin(tournamentId, team.teamId)
        TeamToTournamentInsertAsyncTask(tournamentTeamJoinDao).execute(tournamentTeamJoin)
    }

    fun removeTeamFromTournament(team: Team, tournamentId: Long) {
        val tournamentTeamJoin = TournamentTeamJoin(tournamentId, team.teamId)
        TeamToTournamentDeleteAsyncTask(tournamentTeamJoinDao).execute(tournamentTeamJoin)
    }

    fun getTeamById(id: Long): LiveData<Team?> = teamDao.getTeamById(id)

    fun update(team: Team) { TeamUpdateAsyncTask(teamDao).execute(team) }
}

class TeamInsertAsyncTask(private val teamDao: TeamDao) : AsyncTask<Team, Void, Long>() {
    override fun doInBackground(vararg team: Team): Long {
        return teamDao.insert(team[0])
    }
}

class TeamUpdateAsyncTask(private val teamDao: TeamDao): AsyncTask<Team, Void, Unit>() {
    override fun doInBackground(vararg team: Team) {
        teamDao.update(team[0])
    }
}

class TeamToTournamentInsertAsyncTask(private val tournamentTeamJoinDao: TournamentTeamJoinDao) : AsyncTask<TournamentTeamJoin, Void, Unit>() {
    override fun doInBackground(vararg ttj: TournamentTeamJoin) {
        tournamentTeamJoinDao.insert(ttj[0])
    }
}

class TeamToTournamentDeleteAsyncTask(private val tournamentTeamJoinDao: TournamentTeamJoinDao) : AsyncTask<TournamentTeamJoin, Void, Unit>() {
    override fun doInBackground(vararg ttj: TournamentTeamJoin) {
        tournamentTeamJoinDao.delete(ttj[0])
    }
}

