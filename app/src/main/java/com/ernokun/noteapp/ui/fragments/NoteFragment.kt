package com.ernokun.noteapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel

class NoteFragment(val noteViewModel: NoteViewModel, val note: Note? = null) : Fragment(R.layout.fragment_note) {

    /**
     * The views in this fragment
     */
    private lateinit var textView_noteType: TextView
    private lateinit var toolbar: Toolbar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)

        setupToolbar()
        setupNoteInfo()
    }


    /**
     *  Finds all of the views in this fragment
     */
    private fun findViews(view: View) {
        textView_noteType = view.findViewById(R.id.textView_noteType)
        toolbar = view.findViewById(R.id.toolbar)
    }


    /**
     *  Sets up the toolbar
     */
    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_fragment_note)

        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }


    /**
     * If a note was passed, then make the views show its data
     * this lets the user edit an already existing note.
     */
    private fun setupNoteInfo() {
        if (note == null)
            return

        textView_noteType.text = note.noteText
    }
}