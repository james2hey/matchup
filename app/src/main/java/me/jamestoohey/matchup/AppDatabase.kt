package me.jamestoohey.matchup

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.jamestoohey.matchup.team.TeamDao
import me.jamestoohey.matchup.team.TeamEntity
import me.jamestoohey.matchup.tournament.TournamentDao
import me.jamestoohey.matchup.tournament.TournamentEntity

@Database(entities = [TeamEntity::class, TournamentEntity::class, TournamentTeamJoin::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun tournamentDao(): TournamentDao
    abstract fun tournamentTeamJoinDao(): TournamentTeamJoinDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
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