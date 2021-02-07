package com.ernokun.noteapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.room.applications.NotesApplication
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.room.viewModels.NoteViewModelFactory
import com.ernokun.noteapp.ui.dialogs.AddNoteDialog
import com.ernokun.noteapp.utils.NoteAdapter

class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    private lateinit var noteList: List<Note>

    private lateinit var textViewNoteText: TextView
    private lateinit var addNoteImageView: ImageView
    private lateinit var recyclerView_notes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        prepareNoteAdapter()

        addNoteImageView.setOnClickListener {
//            addNote(Note("A new note that was created from the button"))
//            showToast("Add note was pressed!")
            showAddNoteDialog()
        }

        noteViewModel.allNotes.observe(this, Observer { notes ->
            notes?.let {
                noteList = it
                noteAdapter.submitList(noteList)
            }
        })

    }

    private fun findViews() {
        addNoteImageView = findViewById(R.id.imageView_addNote)
        recyclerView_notes = findViewById(R.id.recyclerView_notes)
    }

    private fun prepareNoteAdapter() {
        noteAdapter = NoteAdapter()
        recyclerView_notes.adapter = noteAdapter
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addNote(note: Note) {
        noteViewModel.insert(note)
    }

    private fun showAddNoteDialog() {
        AddNoteDialog.newInstance().show(supportFragmentManager, AddNoteDialog.TAG)
    }

}