package com.ernokun.noteapp.ui.fragments

import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.applications.NotesApplication
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.room.viewModels.NoteViewModelFactory
import com.ernokun.noteapp.utils.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment(val noteViewModel: NoteViewModel) : Fragment(R.layout.fragment_main) {

    private lateinit var noteList: List<Note>


    private lateinit var editText_searchBar: EditText
    private lateinit var addNoteButton: FloatingActionButton
    private lateinit var recyclerView_notes: RecyclerView

    private lateinit var noteAdapter: NoteAdapter

}