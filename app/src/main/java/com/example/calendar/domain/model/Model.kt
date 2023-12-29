package com.example.calendar.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Month(
    val name: String, val days: Int
)

data class TextButtonParams(
    val text: String, val color: Color
)

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "uid") val uid: Int = 0,

    @ColumnInfo(name = "name") var name: String = "",

    @ColumnInfo(name = "repeat") var repeat: String = "",

    @ColumnInfo(name = "date") var date: String = "",

    @ColumnInfo(name = "time") var time: String = "",

    @ColumnInfo(name = "description") var description: String = ""
)