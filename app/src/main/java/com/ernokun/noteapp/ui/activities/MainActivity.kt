package com.ernokun.noteapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.applications.NotesApplication
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.room.viewModels.NoteViewModelFactory
import com.ernokun.noteapp.ui.dialogs.AddNoteDialog
import com.ernokun.noteapp.ui.dialogs.UpdateNoteDialog
import com.ernokun.noteapp.ui.fragments.MainFragment
import com.ernokun.noteapp.utils.NoteAdapter

class MainActivity : AppCompatActivity(), AddNoteDialog.AddNoteDialogListener,
    NoteAdapter.OnPressedNoteItem, UpdateNoteDialog.UpdateNoteDialogListener {


    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    private lateinit var mainFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = MainFragment(noteViewModel)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view_fragmentContainer, mainFragment)
        }
    }


    /**
     *  Utility, primarily used to fix bugs
     */
    fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }


    private fun showAddNoteDialog() {
        AddNoteDialog.newInstance().show(supportFragmentManager, AddNoteDialog.TAG)
    }


    override fun saveNote(note: Note) {
        noteViewModel.insert(note)
    }


    /**
     *  Open the UpdateNoteDialog window
     */
    override fun editNote(note: Note) {
        UpdateNoteDialog.newInstance(note).show(supportFragmentManager, AddNoteDialog.TAG)
    }


    /**
     *  Implemented in the NoteAdapter
     */
    override fun deleteNote(note: Note) {
        noteViewModel.delete(note)
        showToast("Note was deleted!")
    }


    /**
     *  Update the note from UpdateNoteDialog
     */
    override fun updateNote(note: Note) {
        noteViewModel.update(note)
    }

}