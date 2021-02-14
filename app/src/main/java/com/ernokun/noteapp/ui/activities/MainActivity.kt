package com.ernokun.noteapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.applications.NotesApplication
import com.ernokun.noteapp.room.entities.Note
import com.ernokun.noteapp.room.viewModels.NoteViewModel
import com.ernokun.noteapp.room.viewModels.NoteViewModelFactory
import com.ernokun.noteapp.ui.dialogs.AddNoteDialog
import com.ernokun.noteapp.ui.dialogs.UpdateNoteDialog
import com.ernokun.noteapp.ui.fragments.MainFragment
import com.ernokun.noteapp.ui.fragments.NoteFragment
import com.ernokun.noteapp.utils.NoteAdapter

class MainActivity : AppCompatActivity(), NoteAdapter.OnPressedNoteItem, MainFragment.NoteListener {


    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    private lateinit var mainFragment: Fragment

    private var currentlyInMainFragment: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = MainFragment(noteViewModel)

        // Displays the MainFragment by default
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view_fragmentContainer, mainFragment)
        }
    }


    /**
     *  Utility, primarily used to fix bugs
     */
    fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }


    /**
     *  Open the UpdateNoteDialog window
     */
    override fun onEditNotePressed(note: Note) {
        showFragment(NoteFragment(noteViewModel, note))
    }


    /**
     *  Implemented in the NoteAdapter
     */
    override fun onDeleteNotePressed(note: Note) {
        noteViewModel.delete(note)
        showToast("Note was deleted!")
    }



    /**
     *  Called from MainFragment, when the create note button is pressed.
     */
    override fun onAddNotePressed() {
        showFragment(NoteFragment(noteViewModel))
    }


    /**
     *  Displays the passed fragment in the FragmentContainerView
     */
    private fun showFragment(fragment: Fragment) {
        // Changes the functionality of the onBackPressed function
        currentlyInMainFragment = fragment === mainFragment

        // Displays the passed fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view_fragmentContainer, fragment)
        }
    }


    /**
     *  If back is pressed while in NoteFragment, then the User should return to the MainFragment
     *  If the user tries to go back while in MainFragment - the application closes
     */
    override fun onBackPressed() {
        if (currentlyInMainFragment)
            finish()
        else
            showFragment(mainFragment)
    }
}