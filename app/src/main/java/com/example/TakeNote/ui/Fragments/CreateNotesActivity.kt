package com.example.TakeNote.ui.Fragments

import android.os.Bundle
import android.os.PersistableBundle
import android.text.format.DateFormat
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.TakeNote.Model.Note
import com.example.TakeNote.Model.notesList
import com.example.TakeNote.R
import com.example.TakeNote.databinding.ActivityCreateNotesBinding
import java.util.*


class CreateNotesActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onCreateNoteButtonClick()
        supportActionBar?.title = resources.getString(R.string.create_new_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun onCreateNoteButtonClick() {
        binding.btnCreateNote.setOnClickListener {
            val title = binding.edtNoteTitle.text.toString()
            val details = binding.edtNoteDetails.text.toString()
            val notesDate = getCurrentDate()

            if (validateTextFields(title,details)) {
                val note = Note(
                    title = title,
                    details = details,
                    date = notesDate,
                    isArchived = intent.getBooleanExtra("isArchived",false)
                )
                notesList.add(note)
                finish()

            }else{
                Toast.makeText(this,"Please fill the required fields!",Toast.LENGTH_LONG).show()
            }



        }
    }

    private fun validateTextFields(title:String,details:String):Boolean {
        return title.isNotEmpty() || details.isNotEmpty()
    }

    private fun getCurrentDate(): String {
        val date = Calendar.getInstance().time
        return DateFormat.format("dd MMMM, yyyy", date).toString()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}