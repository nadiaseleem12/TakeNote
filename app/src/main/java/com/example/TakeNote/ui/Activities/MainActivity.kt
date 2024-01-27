package com.example.TakeNote.ui.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.TakeNote.R
import com.example.TakeNote.databinding.ActivityMainBinding
import com.example.TakeNote.ui.Fragments.NotesFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.app_name)
        pushFragment(NotesFragment())

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.notes_menu_item ->
                    pushFragment(NotesFragment())

                R.id.archived_notes_menu_item ->
                    pushFragment(ArchivedNotesFragment())
            }
            true
        }


    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }


}

