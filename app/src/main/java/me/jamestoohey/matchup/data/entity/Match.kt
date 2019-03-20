package me.jamestoohey.matchup.data.entity

import android.arch.persistence.room.*

@Entity(tableName = "matches",
    foreignKeys = [
        ForeignKey(entity = Tournament::class, parentColumns = ["tournamentId"], childColumns = ["toid"]),
        ForeignKey(entity = Team::class, parentColumns = ["teamId"], childColumns = ["htid"]),
        ForeignKey(entity = Team::class, parentColumns = ["teamId"], childColumns = ["atid"])
    ],
    indices = [Index("toid"), Index("htid"), Index("atid")])
data class Match(
    var toid: Long,
    var htid: Long?,
    var atid: Long?,
    var matchName: String
) {
    @PrimaryKey(autoGenerate = true) var matchId: Long = 0
    @ColumnInfo(name = "home_goals") var homeGoals: Int? = null
    @ColumnInfo(name = "away_goals") var awayGoals: Int? = null
}