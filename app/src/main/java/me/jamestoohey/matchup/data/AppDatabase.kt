package me.jamestoohey.matchup.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.jamestoohey.matchup.data.dao.MatchDao
import me.jamestoohey.matchup.data.dao.TournamentTeamJoinDao
import me.jamestoohey.matchup.data.entity.TournamentTeamJoin
import me.jamestoohey.matchup.data.dao.TeamDao
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.data.dao.TournamentDao
import me.jamestoohey.matchup.data.entity.Match
import me.jamestoohey.matchup.data.entity.Tournament

@Database(entities = [Team::class, Tournament::class, TournamentTeamJoin::class, Match::class], version = 7)
abstract class AppDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun tournamentDao(): TournamentDao
    abstract fun tournamentTeamJoinDao(): TournamentTeamJoinDao
    abstract fun matchDao(): MatchDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, "MATCHUP_DB")
                .fallbackToDestructiveMigration()
                .build()
        }


    }
}