package com.ernokun.noteapp.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel

class NoteFragment(val noteViewModel: NoteViewModel, val note: Note? = null) : Fragment(R.layout.fragment_note) {

    private lateinit var textView_noteType: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        note?.let { textView_noteType.text = note.noteText }
    }

    private fun findViews(view: View) {
        textView_noteType = view.findViewById(R.id.textView_noteType)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}