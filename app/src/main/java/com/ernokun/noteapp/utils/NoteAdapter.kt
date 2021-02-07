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

class NoteAdapter(context: Context) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteComparator()) {

    private var listener: OnPressedNoteItem? = null

    public interface OnPressedNoteItem {
        fun editNote(note: Note)
    }

    init {
        if (context is OnPressedNoteItem)
            listener = context
        else
            throw RuntimeException("Context must be an instance of OnPressedNoteItem!")
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val constraintLayout_note: ConstraintLayout = itemView.findViewById(R.id.constraintLayout_note)
        private val textView_noteDate: TextView = itemView.findViewById(R.id.textView_noteDate)
        private val textView_noteText: TextView = itemView.findViewById(R.id.textView_noteText)

        fun bind(note: Note, listener: OnPressedNoteItem?) {
            textView_noteDate.text = note.formattedDate
            textView_noteText.text = note.noteText

            constraintLayout_note.setOnClickListener {
                listener?.editNote(note)
            }
        }

        companion object {
            private var noteSizeCounter: Int = 1

            fun create(parent: ViewGroup): NoteViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(getItemLayout(), parent, false)

                noteSizeCounter++

                if (noteSizeCounter > 3)
                    noteSizeCounter = 2

                return NoteViewHolder(view)
            }

            private fun getItemLayout(): Int {
                return if (noteSizeCounter != 2) R.layout.item_note_small else R.layout.item_note_big
            }
        }
    }

    class NoteComparator : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
}