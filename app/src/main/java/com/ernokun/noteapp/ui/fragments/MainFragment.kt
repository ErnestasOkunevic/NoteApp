package com.ernokun.noteapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.utils.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment(val noteViewModel: NoteViewModel) : Fragment(R.layout.fragment_main) {

    private lateinit var noteList: List<Note>

    private lateinit var editText_searchBar: EditText
    private lateinit var addNoteButton: FloatingActionButton
    private lateinit var recyclerView_notes: RecyclerView

    private lateinit var noteAdapter: NoteAdapter

    private var listener: NoteListener? = null


    interface NoteListener {
        fun onAddNotePressed()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        prepareNoteAdapter()

        observeNoteData()

        initAddButtonListener()
        initSearchBarListener()
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
            listener?.onAddNotePressed()
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

    private fun filterList(noteFilter: String) {
        val noteFilterCaseLowered = noteFilter.toLowerCase()

        val filteredNoteList: List<Note> =
            noteList.filter { note ->
                val noteString = "${note.noteTitle} ${note.noteText}".toLowerCase()
                noteString.contains(noteFilterCaseLowered)
            }

        noteAdapter.submitList(filteredNoteList);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is NoteListener)
            listener = context
        else
            throw RuntimeException("You need to implement AddNoteListener in your activity!")
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }
}