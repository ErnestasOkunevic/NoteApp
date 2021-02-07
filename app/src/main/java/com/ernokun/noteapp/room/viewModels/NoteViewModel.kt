package com.ernokun.noteapp.room.viewModels

import androidx.lifecycle.*
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.repositories.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository): ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(note: Note) = viewModelScope.launch { repository.insert(note) }


    /**
     * Launching a new coroutine to update the data in a non-blocking way
     */
    fun update(note: Note) = viewModelScope.launch { repository.update(note) }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}