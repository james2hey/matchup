package me.jamestoohey.matchup.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import me.jamestoohey.matchup.data.entity.Match

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches  WHERE toid=:tournamentId")
    fun getAllMatchesForTournament(tournamentId: Long): LiveData<List<Match>>

    @Query("SELECT * FROM matches WHERE matchId=:id")
    fun getMatchById(id: Long): LiveData<Match>

    @Insert
    fun insert(match: Match): Long

    @Delete
    fun delete(match: Match)

    @Query("DELETE FROM matches WHERE toid=:tournamentId")
    fun deleteMatchesForTournament(tournamentId: Long)

}