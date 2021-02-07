package com.ernokun.noteapp.room.repositories

import androidx.annotation.WorkerThread
import com.ernokun.noteapp.room.daos.NoteDao
import com.ernokun.noteapp.room.entities.Note
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class NoteRepository(private val noteDao: NoteDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }


    /**
     * TODO implement the rest of the functions:
     * update
     * delete
     * deleteAll
     */

}