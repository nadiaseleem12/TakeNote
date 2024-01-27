package com.example.TakeNote.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.TakeNote.Model.notesList
import com.example.TakeNote.databinding.FragmentArchivedNotesBinding
import com.example.TakeNote.Adapters.NotesAdapter
import com.example.TakeNote.Model.Note
import com.example.TakeNote.R
import com.example.TakeNote.databinding.DialogDeleteBinding
import com.example.TakeNote.ui.Fragments.CreateNotesActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class ArchivedNotesFragment : Fragment() {

    lateinit var binding: FragmentArchivedNotesBinding
    lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchivedNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        onAddNotesButtonClick()


    }


    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        adapter.setMyNotesList(notesList.filter { note -> note.isArchived }.toMutableList())

    }
    private fun prepareRecyclerView() {
        adapter = NotesAdapter()
        binding.rvAllArchivedNotes.adapter = adapter

        adapter.setMyNotesList(notesList.filter { note: Note -> note.isArchived }.toMutableList())

        binding.rvAllArchivedNotes.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        onDeleteClick()
        onArchiveClick()
    }

    private fun onDeleteClick() {
        adapter.deleteListener = NotesAdapter.OnDeleteClickListener{ position ->
            displayDeleteBottomSheetDialog(position)
        }
    }

    private fun displayDeleteBottomSheetDialog(position:Int) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
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
        adapter.archiveListener = NotesAdapter.OnArchiveClickListener{note:Note, position ->
            note.isArchived = false
            adapter.setListItem(note,position)
            adapter.setMyNotesList(notesList.filter { note -> note.isArchived }.toMutableList())

        }
    }

    private fun onAddNotesButtonClick() {
        binding.btnAddNotes.setOnClickListener {
            val intent = Intent(requireContext(), CreateNotesActivity::class.java)
            intent.putExtra("isArchived", true)
            startActivity(intent)
        }
    }

}