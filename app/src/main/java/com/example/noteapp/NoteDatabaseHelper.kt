package com.example.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null, VERSION) {
    companion object{
        const val DATABASE_NAME = "nodesapp.db"
        const val VERSION = 1
        const val TABLE_NAME = "allnotes"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("Create table $TABLE_NAME ($COLUMN_ID integer primary key autoincrement,$COLUMN_TITLE text,$COLUMN_CONTENT text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertNote(note: Note){
        var db = writableDatabase
        var values = ContentValues()
        values.put(COLUMN_TITLE,note.title)
        values.put(COLUMN_CONTENT,note.content)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllNotes():List<Note>{
        var notesList = mutableListOf<Note>()
        var db = readableDatabase
        var cursor = db.rawQuery("select * from ${TABLE_NAME}",null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val note = Note(id,title,content)
            notesList.add(note)
        }
        db.close()
        return  notesList
    }

    fun updateNote(note:Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_CONTENT,note.content)
        }
        db.update(TABLE_NAME,values,"${COLUMN_ID}=?", arrayOf(note.id.toString()))
        db.close()
    }

    fun getNoteById( id:Int):Note{
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${TABLE_NAME} where ${COLUMN_ID}=$id",null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Note(id, title, content)
    }

    fun delNoteById(id:Int){
        val db = writableDatabase
        db.delete(TABLE_NAME,"$COLUMN_ID=?", arrayOf(id.toString()))

        db.close()
    }

}