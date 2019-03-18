package me.jamestoohey.matchup.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tournaments")
data class Tournament(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "group_stages") var groupStages: Boolean
) {
    @PrimaryKey(autoGenerate = true) var tournamentId: Long = 0
}