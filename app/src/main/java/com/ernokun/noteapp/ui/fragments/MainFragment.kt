package com.ernokun.noteapp.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.applications.NotesApplication
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.room.viewModels.NoteViewModelFactory
import com.ernokun.noteapp.ui.dialogs.AddNoteDialog
import com.ernokun.noteapp.utils.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment(val noteViewModel: NoteViewModel) : Fragment(R.layout.fragment_main) {

    private lateinit var noteList: List<Note>

    private lateinit var editText_searchBar: EditText
    private lateinit var addNoteButton: FloatingActionButton
    private lateinit var recyclerView_notes: RecyclerView

    private lateinit var noteAdapter: NoteAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        prepareNoteAdapter()

        observeNoteData()

        initAddButtonListener()
        initSearchBarListener()
    }

    private fun observeNoteData() {
        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let {
                noteList = it
                noteAdapter.submitList(noteList)
            }
        })
    }

    private fun initAddButtonListener() {
        addNoteButton.setOnClickListener {
            /**
             *  TODO make an interface for this fragment and implement it in the MainActivity
             *  it should change the fragment to a new one
             */
        }
    }

    private fun initSearchBarListener() {
        editText_searchBar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                val filterString: String = editText_searchBar.text.toString()
                filterList(filterString)

                return@OnKeyListener true
            }
            false
        })
    }

    private fun findViews(view: View) {
        editText_searchBar = view.findViewById(R.id.editText_searchBar)
        addNoteButton = view.findViewById(R.id.floatingActionButton_addNote)
        recyclerView_notes = view.findViewById(R.id.recyclerView_notes)
    }

    private fun prepareNoteAdapter() {
        noteAdapter = context?.let { NoteAdapter(it) }!!
        recyclerView_notes.adapter = noteAdapter
    }

    private fun filterList(noteFilter: String) {
        val filteredNoteList: List<Note> =
            noteList.filter { note ->
                note.noteText.toLowerCase().contains(noteFilter.toLowerCase())
            }

        noteAdapter.submitList(filteredNoteList);
    }
}