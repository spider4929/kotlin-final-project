package com.example.pathfinder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "path_table")
data class Path (
    @PrimaryKey(autoGenerate = true)
    var pathId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "source")
    var source: String,

    @ColumnInfo(name = "destination")
    var destination: String,

    @ColumnInfo(name = "description")
    var description: String

    )