package me.jamestoohey.matchup.team

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAllTeams(): LiveData<List<TeamEntity>>

    @Insert
    fun insert(teamEntity: TeamEntity)
}

