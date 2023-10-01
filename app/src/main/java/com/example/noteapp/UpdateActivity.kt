package com.example.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteapp.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBinding
    lateinit var db:NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id:Int = intent.getIntExtra("node_id",-1)

        db = NoteDatabaseHelper(this)
        var note:Note = db.getNoteById(id)

        binding.edtTitle.setText(note.title)
        binding.edtDesc.setText(note.content)

        binding.btnUpdateNote.setOnClickListener {
            note.content = binding.edtDesc.text.toString()
            note.content = binding.edtDesc.text.toString()
            db.updateNote(note)
            db.close()
            finish()
        }
    }
}