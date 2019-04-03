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

    @Insert
    fun insert(team: Team): Long

    @Query("SELECT * FROM teams WHERE teamId=:id")
    fun getTeamById(id: Long): LiveData<Team?>

//    @Query("UPDATE teams SET name=:teamName AND image_path=:teamImage WHERE teamId=:teamId")
    @Update
    fun update(team: Team)
}

