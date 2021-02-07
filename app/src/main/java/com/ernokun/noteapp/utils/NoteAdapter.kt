package com.ernokun.noteapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.noteapp.R
import com.ernokun.noteapp.room.entities.Note

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView_noteDate: TextView = itemView.findViewById(R.id.textView_noteDate)
        private val textView_noteText: TextView = itemView.findViewById(R.id.textView_noteText)

        fun bind(note: Note) {
            textView_noteDate.text = note.creationDate
            textView_noteText.text = note.noteText

//            layout_note.setOnClickListener {
//                // interface that gives data and signal to mainActivity,
//                // where EditActivity is called
//                // context(MainActivity).editNote(idk what to put here yet)
//                if (context is OnPressedNoteItem) context.editNote(note.id)
//            }
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