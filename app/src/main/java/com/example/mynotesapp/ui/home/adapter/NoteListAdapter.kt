package com.example.mynotesapp.ui.home.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.R
import com.example.mynotesapp.data.remote.response.noteResponse.Note
import com.example.mynotesapp.databinding.ItemNoteBinding
import com.example.mynotesapp.utils.extension.OnItemClickListener

/**
 *Created by Mert Melih Aytemur on 23.06.2022.
 */
class NoteListAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    var noteList = mutableListOf<Note>()

    class ViewHolder(private val itemBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(listener: OnItemClickListener, note: Note) {
            itemBinding.noteItem = note
            itemBinding.onItemClickListener = listener
            itemBinding.executePendingBindings()

            if (note.priority.equals("high priority")) {
                itemBinding.cvPriorityIndicator.setCardBackgroundColor(Color.RED)
            }
            if (note.priority.equals("medium priority")) {
                itemBinding.cvPriorityIndicator.setCardBackgroundColor(Color.YELLOW)
            }
            if (note.priority.equals("low priority")
            ) {
                itemBinding.cvPriorityIndicator.setCardBackgroundColor(Color.GREEN)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(onItemClickListener, noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    fun updateList(_noteList: MutableList<Note>) {
        noteList = _noteList
        notifyDataSetChanged()
    }
}