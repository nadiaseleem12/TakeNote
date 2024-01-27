package com.example.TakeNote.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.TakeNote.Model.Note
import com.example.TakeNote.R
import com.example.TakeNote.databinding.ItemNoteBinding

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    private var notesList = mutableListOf<Note>()

    fun setMyNotesList(list: MutableList<Note>) {
        notesList = list
        notifyDataSetChanged()
    }
    fun removeNoteFromList(index: Int){
        notesList.removeAt(index)
        notifyItemRemoved(index)

    }

    fun setListItem(note: Note,position: Int){
        notesList[position] = note
        notifyItemChanged(position)

    }

   class NotesViewHolder(val binding: ItemNoteBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(note: Note){
           binding.apply {
               title.text = note.title
               details.text = note.details
               date.text = note.date
           }

           if (note.isArchived){
               binding.archiveImv.setImageResource(R.drawable.ic_unarchive)
           }else{
               binding.archiveImv.setImageResource(R.drawable.ic_archive)
           }

       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
       val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notesList[position]
        holder.bind(note)


        archiveListener?.let { archiveListener->
            holder.binding.archiveImv.setOnClickListener {
                archiveListener.onClick(note,position)
            }
        }

        deleteListener?.let { deleteListener->
            holder.binding.deleteImv.setOnClickListener {
                deleteListener.onClick(position)
            }
        }

    }

    var archiveListener :OnArchiveClickListener?= null
    fun interface OnArchiveClickListener{
        fun onClick(note: Note,position: Int)
    }

    var deleteListener :OnDeleteClickListener?= null
    fun interface OnDeleteClickListener{
        fun onClick(position: Int)
    }
}
