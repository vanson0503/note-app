package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db:NoteDatabaseHelper
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= NoteDatabaseHelper(this)
        noteAdapter = NoteAdapter(db.getAllNotes(),this)

        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this,AddNoteActivity::class.java)
            startActivity(intent)
        }

        binding.rvListNotes.layoutManager = LinearLayoutManager(this)
        binding.rvListNotes.adapter = noteAdapter

    }

    override fun onResume() {
        noteAdapter.refreshData(db.getAllNotes())
        super.onResume()
    }
}