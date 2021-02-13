package com.ernokun.noteapp.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "text") var noteText: String,
    @ColumnInfo(name = "title") var noteTitle: String = getNoteTitleName(),
    @ColumnInfo(name = "creation_date") var creationDate: String = LocalDateTime.now().toString(),
    @PrimaryKey val id: String = UUID.randomUUID().toString()
) {

    companion object {
        var noteCount: Int = 0

        fun getNoteTitleName(): String {
            return "Note${noteCount++}"
        }
    }

    fun getFormattedDate(): String {
        val localDateTime: LocalDateTime = LocalDateTime.parse(creationDate)
        val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        // The space at the end is necessary because the Italic font is used.
        // It basically prevents the date from being cut at the end...
        return "${dateTimeFormatter.format(localDateTime)} "
    }
}