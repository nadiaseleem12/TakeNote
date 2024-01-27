package com.example.TakeNote.ui.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.TakeNote.databinding.FragmentNotesBinding
import com.example.TakeNote.Adapters.NotesAdapter
import com.example.TakeNote.Model.notesList
import com.example.TakeNote.R
import com.example.TakeNote.databinding.DialogDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class NotesFragment : Fragment() {

    lateinit var binding: FragmentNotesBinding
    lateinit var adapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        onAddNoteButtonClick()


    }

    private fun prepareRecyclerView() {
        adapter = NotesAdapter()
        binding.rvAllNotes.adapter = adapter
        adapter.setMyNotesList(notesList)
        binding.rvAllNotes.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        onArchiveClick()

        onDeleteClick()
    }

    private fun onDeleteClick() {
        adapter.deleteListener = NotesAdapter.OnDeleteClickListener{  position ->
            displayDeleteBottomSheetDialog(position)
        }
    }
    private fun displayDeleteBottomSheetDialog(position:Int) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
        val binding = DialogDeleteBinding.inflate(requireActivity().layoutInflater)
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()
        binding.noBtn.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        binding.yesBtn.setOnClickListener {
            notesList.removeAt(position)
            adapter.removeNoteFromList(position)
            bottomSheetDialog.dismiss()
        }
    }

    private fun onArchiveClick() {
      adapter.archiveListener = NotesAdapter.OnArchiveClickListener{note, position ->
          note.isArchived = true
          adapter.setListItem(note,position)
          adapter.setMyNotesList(notesList.filter { note -> !note.isArchived }.toMutableList())

      }
    }



    override fun onResume() {
        super.onResume()
        adapter.setMyNotesList(notesList.filter { note -> !note.isArchived }.toMutableList())

    }


    private fun onAddNoteButtonClick() {
        binding.btnAddNotes.setOnClickListener {
            val intent =  Intent(requireContext(),CreateNotesActivity::class.java)
            intent.putExtra("isArchived",false)
            startActivity(intent)
        }

    }






}