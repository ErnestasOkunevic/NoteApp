package com.ernokun.noteapp.ui.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel

class NoteFragment(private val noteViewModel: NoteViewModel, val note: Note? = null) :
    Fragment(R.layout.fragment_note) {

    /**
     * The views in this fragment
     */
    private lateinit var textView_noteDate: TextView
    private lateinit var editText_noteTitle: EditText
    private lateinit var editText_noteText: EditText

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
        textView_noteDate = view.findViewById(R.id.textView_noteDateModified)
        editText_noteTitle = view.findViewById(R.id.editText_noteTitleModified)
        editText_noteText = view.findViewById(R.id.editText_noteTextModified)
        toolbar = view.findViewById(R.id.toolbar)
    }


    /**
     *  Sets up the toolbar
     */
    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.menu_fragment_note)

        // Listener for the go back arrow icon
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }


        // Listener for the toolbar items
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_save -> {
                    saveOrUpdateNote()
                    activity?.onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
    }


    /**
     *  Called when the save icon/menuItem is selected in the toolbar.
     *  Either creates a new note or updates an existing one.
     */
    private fun saveOrUpdateNote() {
        val newNoteData: Note = getNoteFromViews()

        if (note == null)
            noteViewModel.insert(
                Note(
                    noteText = newNoteData.noteText,
                    noteTitle = newNoteData.noteTitle
                )
            )
        else
            noteViewModel.update(
                Note(
                    noteText = newNoteData.noteText,
                    noteTitle = newNoteData.noteTitle,
                    id = note.id
                )
            )
    }


    /**
     * Gets the user inputted data
     */
    private fun getNoteFromViews(): Note {
        val title: String = editText_noteTitle.text.toString()
        val text: String = editText_noteText.text.toString()

        return if (title.isBlank())
            Note(noteText = text)
        else
            Note(noteText = text, noteTitle = title)
    }


    /**
     * If a note was passed, then make the views show its data
     * this lets the user edit an already existing note.
     */
    private fun setupNoteInfo() {
        if (note == null) {
            val dateString = Note("").getFormattedDate()
            textView_noteDate.text = dateString

            return
        }

        // Sets up the date text
        val dateString = "Modified: ${note.getFormattedDate()}"
        textView_noteDate.text = dateString

        // Sets up the title text
        editText_noteTitle.setText(note.noteTitle)

        // Sets up the title text
        editText_noteText.setText(note.noteText)
    }
}