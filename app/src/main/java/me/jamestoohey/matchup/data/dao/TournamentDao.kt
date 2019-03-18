package me.jamestoohey.matchup.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.jamestoohey.matchup.data.entity.Tournament

@Dao
interface TournamentDao {
    @Query("SELECT * FROM tournaments")
    fun getAllTournaments(): LiveData<List<Tournament>>

    @Query("SELECT * FROM tournaments WHERE tournamentId = :id")
    fun getTournament(id: Long): Tournament

    @Insert
    fun insert(team: Tournament): Long
    //    fun
}