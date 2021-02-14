package com.ernokun.noteapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.utils.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment(val noteViewModel: NoteViewModel) : Fragment(R.layout.fragment_main) {


    /**
     * This is a reference to the noteList that is provided by the noteViewModel
     * It is used primarily for the filterList function
     */
    private lateinit var noteList: List<Note>


    /**
     * The views in this fragment
     */
    private lateinit var editText_searchBar: EditText
    private lateinit var addNoteButton: FloatingActionButton
    private lateinit var recyclerView_notes: RecyclerView


    /**
     * The note adapter for the recyclerView_notes RecyclerView
     */
    private lateinit var noteAdapter: NoteAdapter


    /**
     * MainActivity is later passed to this variable via the onAttach method
     */
    private var listener: NoteListener? = null


    /**
     * This interface is used for communication between this MainFragment and MainActivity
     */
    interface NoteListener {
        fun onAddNotePressed()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        setupNoteAdapter()
        setupNoteDataObservation()
        setupAddButtonListener()
        setupSearchBarListener()
    }


    /**
     *  Finds all of the views in this fragment
     */
    private fun findViews(view: View) {
        editText_searchBar = view.findViewById(R.id.editText_searchBar)
        addNoteButton = view.findViewById(R.id.floatingActionButton_addNote)
        recyclerView_notes = view.findViewById(R.id.recyclerView_notes)
    }


    /**
     * Sets up the NoteAdapter for the recyclerview
     */
    private fun setupNoteAdapter() {
        noteAdapter = context?.let { NoteAdapter(it) }!!
        recyclerView_notes.adapter = noteAdapter
    }


    /**
     * Sets up the note data observation from the ROOM database
     */
    private fun setupNoteDataObservation() {
        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let {
                noteList = it
                noteAdapter.submitList(noteList)
            }
        })
    }


    /**
     *  Sets up the add note button listener
     */
    private fun setupAddButtonListener() {
        addNoteButton.setOnClickListener {
            listener?.onAddNotePressed()
        }
    }


    /**
     *  Sets up the search bar listener
     */
    private fun setupSearchBarListener() {
        editText_searchBar.addTextChangedListener {
            val filterString: String = editText_searchBar.text.toString()
            filterList(filterString)
        }
    }


    /**
     *  Filters all of the notes by a string and then shows the result
     *  in the NoteAdapter list
     */
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
            throw RuntimeException("You need to implement NoteListener in your activity!")
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }
}