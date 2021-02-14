package com.ernokun.noteapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note
import java.lang.RuntimeException

class NoteAdapter(context: Context) :
    ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteComparator()) {


    /**
     * MainActivity is later passed to this variable via the onAttach method from MainFragment
     */
    private var listener: OnPressedNoteItem? = null


    /**
     * This interface is used for communication between this NoteAdapter and MainActivity
     */
    interface OnPressedNoteItem {
        fun onEditNotePressed(note: Note)
        fun onDeleteNotePressed(note: Note)
    }


    /**
     * Checks whether the provided context can be used as a OnPressedNoteItem interface listener
     */
    init {
        if (context is OnPressedNoteItem)
            listener = context
        else
            throw RuntimeException("Context must be an instance of OnPressedNoteItem!")
    }


    /**
     * Calls the createNoteItem function
     * P.S. the function is called NoteViewHolder.create
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }


    /**
     * Sets up the views in the NoteItem and displays the note data
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }


    /**
     * This is basically how each note is transformer into a NoteItem for the ListAdapter
     */
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         *  The views in each NoteItem
         */
        private val constraintLayout_note: ConstraintLayout =
            itemView.findViewById(R.id.constraintLayout_note)
        private val textView_noteDate: TextView = itemView.findViewById(R.id.textView_noteDate)
        private val textView_noteText: TextView = itemView.findViewById(R.id.textView_noteText)
        private val textView_noteTitle: TextView = itemView.findViewById(R.id.textView_noteTitle)


        /**
         * Sets up the views in each of the NoteItems
         */
        fun bind(note: Note, listener: OnPressedNoteItem?) {
            textView_noteDate.text = note.getFormattedDate();
            textView_noteText.text = note.noteText
            textView_noteTitle.text = note.noteTitle

            // When a note is pressed it should be opened in a new fragment to be edited
            constraintLayout_note.setOnClickListener {
                listener?.onEditNotePressed(note)
            }

            // When a note is pressed on by the user for a longer period of time it is deleted
            constraintLayout_note.setOnLongClickListener {
                listener?.onDeleteNotePressed(note)
                true
            }

        }

        /**
         * Basically a static method holder that knows how to create the NoteItems
         */
        companion object {
            /**
             *  Used to determine whether a small or big note layout should be used
             */
            private var noteSizeCounter: Int = 1


            /**
             * Called in the onCreateViewHolder function.
             * It creates and returns a NoteItem
             */
            fun create(parent: ViewGroup): NoteViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(getItemLayout(), parent, false)

                if (++noteSizeCounter > 3)
                    noteSizeCounter = 2

                return NoteViewHolder(view)
            }


            /**
             * Determine whether a small or big note layout should be used
             */
            private fun getItemLayout(): Int {
                return if (noteSizeCounter != 2) R.layout.item_note_small else R.layout.item_note_big
            }
        }
    }


    /**
     * This is basically how the ListAdapter compares the notes
     */
    class NoteComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteText == newItem.noteText
        }

    }
}