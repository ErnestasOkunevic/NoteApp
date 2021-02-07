package com.ernokun.noteapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*


@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "text") var noteText: String,
    @ColumnInfo(name = "creation_date") val creationDate: String = LocalDate.now().toString(),
    @PrimaryKey val id: String = UUID.randomUUID().toString()
)