package com.ernokun.noteapp.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note
import java.time.LocalDateTime

class UpdateNoteDialog() : DialogFragment() {

    private lateinit var button_saveNote: Button
    private lateinit var button_discardChanges: Button
    private lateinit var editTextTextMultiLine_noteText: EditText

    private var listener: UpdateNoteDialogListener? = null

    private lateinit var note: Note

    fun setNote(note: Note) {
        this.note = note
    }


    companion object {

        const val TAG = "UpdateNoteDialog"
        private const val KEY_NOTE_TEXT = "KEY_NOTE_TEXT"

        fun newInstance(note: Note): UpdateNoteDialog {
            val fragment = UpdateNoteDialog()
            fragment.setNote(note)

            return fragment
        }
    }


    interface UpdateNoteDialogListener {
        fun updateNote(note: Note)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.note = note

        setupViews(view)
        setupOnClickListeners()
        setupViewText()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
        )

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun setupViews(view: View) {
        button_saveNote = view.findViewById(R.id.button_saveNote)
        button_discardChanges = view.findViewById(R.id.button_discardChanges)
        editTextTextMultiLine_noteText = view.findViewById(R.id.editTextTextMultiLine_noteText)
    }

    private fun setupOnClickListeners() {
        button_saveNote.setOnClickListener {
            // tell activity to update note
            val noteTextString: String = editTextTextMultiLine_noteText.text.toString()
            
            // this was causing a bug!
//            note.noteText = noteText
//            note.updateDate()



            listener?.updateNote(Note(id = note.id, noteText = noteTextString))

            dismiss()
        }

        button_discardChanges.setOnClickListener {
            dismiss()
        }
    }

    private fun setupViewText() {
        editTextTextMultiLine_noteText.setText(note.noteText)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is UpdateNoteDialogListener)
            listener = context
        else
            throw RuntimeException("You need to implement UpdateNoteDialogListener in your activity....")
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }
}