package com.ernokun.noteapp

import android.os.Bundle
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.room.applications.NotesApplication
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.room.viewModels.NoteViewModelFactory
import com.ernokun.noteapp.ui.dialogs.AddNoteDialog
import com.ernokun.noteapp.ui.dialogs.UpdateNoteDialog
import com.ernokun.noteapp.ui.fragments.MainFragment
import com.ernokun.noteapp.utils.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), AddNoteDialog.AddNoteDialogListener,
    NoteAdapter.OnPressedNoteItem, UpdateNoteDialog.UpdateNoteDialogListener {


    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

//    private lateinit var fragment_container_view_fragmentContainer: FragmentContainerView
    private lateinit var mainFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        findViews()

        mainFragment = MainFragment(noteViewModel)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view_fragmentContainer, mainFragment)
        }


//        prepareNoteAdapter()
//
//        noteViewModel.allNotes.observe(this, Observer { notes ->
//            notes?.let {
//                noteList = it
//                noteAdapter.submitList(noteList)
//            }
//        })
//
//        addNoteButton.setOnClickListener {
//            showAddNoteDialog()
//        }
//
//        editText_searchBar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//                //Perform Code
//                val filterString: String = editText_searchBar.text.toString()
//                filterList(filterString)
//
//                return@OnKeyListener true
//            }
//            false
//        })
    }


//    private fun findViews() {
////        editText_searchBar = findViewById(R.id.editText_searchBar)
////        addNoteButton = findViewById(R.id.floatingActionButton_addNote)
////        recyclerView_notes = findViewById(R.id.recyclerView_notes)
//
////        fragment_container_view_fragmentContainer = findViewById(R.id.fragment_container_view_fragmentContainer)
//    }
//
//
//    private fun prepareNoteAdapter() {
//        noteAdapter = NoteAdapter(this)
//        recyclerView_notes.adapter = noteAdapter
//    }


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



//    private fun filterList(noteFilter: String) {
//        val filteredNoteList: List<Note> =
//            noteList.filter { note ->
//                note.noteText.toLowerCase().contains(noteFilter.toLowerCase())
//            }
//
//        noteAdapter.submitList(filteredNoteList);
//    }
}