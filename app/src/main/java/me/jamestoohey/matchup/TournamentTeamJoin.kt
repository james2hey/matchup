package me.jamestoohey.matchup

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import me.jamestoohey.matchup.team.TeamEntity
import me.jamestoohey.matchup.tournament.TournamentEntity


@Entity(tableName = "tournament_team_join",
    primaryKeys = ["toid", "teid"],
    foreignKeys = [
        ForeignKey(entity = TournamentEntity::class, parentColumns = ["tournamentId"], childColumns = ["toid"]),
        ForeignKey(entity = TeamEntity::class, parentColumns = ["teamId"], childColumns = ["teid"])],
    indices = [Index("toid"), Index("teid")])
class TournamentTeamJoin(
    var toid: Long,
    var teid: Long
)