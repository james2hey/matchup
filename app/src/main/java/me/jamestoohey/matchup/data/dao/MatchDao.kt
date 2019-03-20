package me.jamestoohey.matchup.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.jamestoohey.matchup.MatchModel
import me.jamestoohey.matchup.data.entity.Match
import me.jamestoohey.matchup.data.entity.Team

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches  WHERE toid=:tournamentId")
    fun getAllMatchesForTournament(tournamentId: Long): LiveData<List<Match>>

    //TODO make this cleaner
    @Query("SELECT toid, matchName, ht.name AS htName, ht.image_path AS htImage, matches.home_goals, at.name AS atName, at.image_path AS atImage, matches.away_goals FROM matches LEFT JOIN teams ht ON matches.htid=ht.teamId LEFT JOIN teams at ON matches.atid=at.teamId WHERE toid=:tournamentId")
    fun getAllMatchesModelsFromTournament(tournamentId: Long): LiveData<List<MatchModel>>

    @Insert
    fun insert(match: Match)

    @Delete
    fun delete(match: Match)

    @Query("DELETE FROM matches WHERE toid=:tournamentId")
    fun deleteMatchesForTournament(tournamentId: Long)

}