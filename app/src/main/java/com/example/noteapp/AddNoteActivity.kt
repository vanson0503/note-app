package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddNoteBinding
    lateinit var db:NoteDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        binding.btnAddNote.setOnClickListener {
            if(binding.edtDesc.text.toString()==""||binding.edtTitle.text.toString()==""){
                Toast.makeText(this, "Data is empty!", Toast.LENGTH_SHORT).show()
            }
            else{
                var title = binding.edtTitle.text.toString()
                var content = binding.edtDesc.text.toString()
                var note:Note = Note(0,title,content)
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}