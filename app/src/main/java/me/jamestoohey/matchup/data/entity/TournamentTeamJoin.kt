package me.jamestoohey.matchup.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index


@Entity(tableName = "tournament_team_join",
    primaryKeys = ["toid", "teid"],
    foreignKeys = [
        ForeignKey(entity = Tournament::class, parentColumns = ["tournamentId"], childColumns = ["toid"]),
        ForeignKey(entity = Team::class, parentColumns = ["teamId"], childColumns = ["teid"])],
    indices = [Index("toid"), Index("teid")])
class TournamentTeamJoin(
    var toid: Long,
    var teid: Long
)