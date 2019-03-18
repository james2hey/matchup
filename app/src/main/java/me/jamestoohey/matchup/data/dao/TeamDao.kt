package me.jamestoohey.matchup.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.jamestoohey.matchup.data.entity.Team

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAllTeams(): LiveData<List<Team>>

    @Insert
    fun insert(team: Team)
}

