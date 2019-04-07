package me.jamestoohey.matchup.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import me.jamestoohey.matchup.data.entity.Team

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAllTeams(): LiveData<List<Team>>

    @Query("SELECT * FROM teams WHERE teamId=:id")
    fun getTeamById(id: Long): LiveData<Team?>

    @Insert
    fun insert(team: Team): Long

    @Update
    fun update(team: Team)
}

