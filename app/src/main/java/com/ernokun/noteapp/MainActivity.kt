package com.ernokun.noteapp

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.room.applications.NotesApplication
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.room.viewModels.NoteViewModelFactory
import com.ernokun.noteapp.ui.dialogs.AddNoteDialog
import com.ernokun.noteapp.ui.dialogs.UpdateNoteDialog
import com.ernokun.noteapp.utils.NoteAdapter

class MainActivity : AppCompatActivity(), AddNoteDialog.AddNoteDialogListener,
    NoteAdapter.OnPressedNoteItem, UpdateNoteDialog.UpdateNoteDialogListener {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    private lateinit var noteList: List<Note>


    private lateinit var editText_searchBar: EditText
    private lateinit var addNoteImageView: ImageView
    private lateinit var recyclerView_notes: RecyclerView

    private lateinit var noteAdapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeStatusBarColor();

        findViews()
        prepareNoteAdapter()

        noteViewModel.allNotes.observe(this, Observer { notes ->
            notes?.let {
                noteList = it
                noteAdapter.submitList(noteList)
            }
        })

        addNoteImageView.setOnClickListener {
            showAddNoteDialog()
        }

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


    private fun findViews() {
        editText_searchBar = findViewById(R.id.editText_searchBar)
        addNoteImageView = findViewById(R.id.imageView_addNote)
        recyclerView_notes = findViewById(R.id.recyclerView_notes)
    }


    private fun prepareNoteAdapter() {
        noteAdapter = NoteAdapter(this)
        recyclerView_notes.adapter = noteAdapter
    }


    /**
     *  Utility, primarily used to fix bugs
     */
    private fun showToast(message: String) {
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


    override fun deleteNote(note: Note) {
        noteViewModel.delete(note)
        showToast("Note was deleted!")
    }


    /**
     *  Update the note from UpdateNoteDialog in the database
     */
    override fun updateNote(note: Note) {
        noteViewModel.update(note)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun changeStatusBarColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.test_bg_main)
    }

    private fun filterList(noteFilter: String) {
        val filteredNoteList: List<Note> =
            noteList.filter { note ->
                note.noteText.toLowerCase().contains(noteFilter.toLowerCase())
            }

        noteAdapter.submitList(filteredNoteList);
    }
}