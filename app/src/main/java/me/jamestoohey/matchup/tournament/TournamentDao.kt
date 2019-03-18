package me.jamestoohey.matchup.tournament

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TournamentDao {
    @Query("SELECT * FROM tournaments")
    fun getAllTournaments(): LiveData<List<TournamentEntity>>

    @Query("SELECT * FROM tournaments WHERE tournamentId = :id")
    fun getTournament(id: Long): TournamentEntity

    @Insert
    fun insert(teamEntity: TournamentEntity): Long
    //    fun
}