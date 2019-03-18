package me.jamestoohey.matchup.team

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import me.jamestoohey.matchup.AppDatabase
import me.jamestoohey.matchup.TournamentTeamJoin
import me.jamestoohey.matchup.TournamentTeamJoinDao

class TeamRepository(val context: Context) {

    private val teamDao: TeamDao = AppDatabase.getInstance(context).teamDao()
    private val tournamentTeamJoinDao = AppDatabase.getInstance(context).tournamentTeamJoinDao()

    fun getAllTeams(): LiveData<List<TeamEntity>> {
        return teamDao.getAllTeams()
    }

    fun insert(teamEntity: TeamEntity) {
        TeamInsertAsyncTask(teamDao).execute(teamEntity)
    }

    fun getTeamsForTournament(id: Long): LiveData<List<TeamEntity>> {
        return tournamentTeamJoinDao.getTeamsForTournament(id)
    }


    fun insertTeamToTournament(teamEntity: TeamEntity, tournamentId: Long) {
        val tournamentTeamJoin = TournamentTeamJoin(tournamentId, teamEntity.teamId)
        TeamToTournamentInsertAsyncTask(tournamentTeamJoinDao).execute(tournamentTeamJoin)
    }

    fun removeTeamFromTournament(teamEntity: TeamEntity, tournamentId: Long) {
        val tournamentTeamJoin = TournamentTeamJoin(tournamentId, teamEntity.teamId)
        TeamToTournamentDeleteAsyncTask(tournamentTeamJoinDao).execute(tournamentTeamJoin)
    }
}

class TeamInsertAsyncTask(private val teamDao: TeamDao) : AsyncTask<TeamEntity, Void, Unit>() {
    override fun doInBackground(vararg team: TeamEntity) {
        teamDao.insert(team[0])
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

