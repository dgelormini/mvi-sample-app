package com.dgelormini.mvisample.presentation.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.dgelormini.mvisample.R
import com.dgelormini.mvisample.domain.Note

typealias ClickListener = (Note) -> Unit

class NoteAdapter(private val clickListener: ClickListener) : Adapter<NoteAdapter.ViewHolder>() {

    private var notes = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false) as ViewGroup
        val viewHolder = ViewHolder(itemContainer)
        itemContainer.setOnClickListener { clickListener(notes[viewHolder.adapterPosition]) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.noteId.text = note.id.toString()
        holder.noteText.text = note.text
    }

    override fun getItemCount() = notes.size

    fun updateNotes(notes: List<Note>) {
        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(this.notes, notes))
        this.notes = notes
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemViewGroup: ViewGroup) : RecyclerView.ViewHolder(itemViewGroup) {
        val noteId: TextView = itemViewGroup.findViewById(R.id.noteId)
        val noteText: TextView = itemViewGroup.findViewById(R.id.noteText)
    }
}