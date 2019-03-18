package me.jamestoohey.matchup.team

import android.arch.persistence.room.*
import me.jamestoohey.matchup.tournament.TournamentEntity


@Entity(tableName = "teams")
data class TeamEntity(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image_path") var imagePath: String?
) {
    @PrimaryKey(autoGenerate = true) var teamId: Long = 0
}

//foreignKeys = [ForeignKey(entity = TournamentEntity::class, parentColumns = ["tournamentId"], childColumns = ["toid"])],
//indices = [Index("toid")])