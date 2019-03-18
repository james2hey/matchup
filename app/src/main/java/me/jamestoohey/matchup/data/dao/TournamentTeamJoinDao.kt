package me.jamestoohey.matchup.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.jamestoohey.matchup.data.entity.TournamentTeamJoin
import me.jamestoohey.matchup.data.entity.Team

@Dao
interface TournamentTeamJoinDao {
    @Insert
    fun insert(tournamentTeamJoin: TournamentTeamJoin)

    @Delete
    fun delete(tournamentTeamJoin: TournamentTeamJoin)

    @Query("SELECT * FROM teams INNER JOIN tournament_team_join ON teams.teamId=tournament_team_join.teid WHERE tournament_team_join.toid=:tournamentId")
    fun getTeamsForTournament(tournamentId: Long): LiveData<List<Team>>

}