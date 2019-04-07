package me.jamestoohey.matchup.data.entity

import android.arch.persistence.room.*


@Entity(tableName = "teams")
data class Team(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image_path") var imagePath: String?
) {
    @PrimaryKey(autoGenerate = true) var teamId: Long = 0
}