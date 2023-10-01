package com.example.noteapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(var notes: List<Note>,context: Context): RecyclerView.Adapter<NoteAdapter.NodeViewHolder>() {
    private  val db:NoteDatabaseHelper = NoteDatabaseHelper(context)
    class NodeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val itemTitle:TextView = itemView.findViewById(R.id.txtTitle)
        val itemContent:TextView = itemView.findViewById(R.id.txtContent)
        val itemUpdateBtn:ImageView = itemView.findViewById(R.id.btnEditNote)
        val itemDelBtn:ImageView = itemView.findViewById(R.id.btnDelNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)

        return NodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NodeViewHolder, position: Int) {
        val note = notes[position]
        holder.itemTitle.setText(note.title)
        holder.itemContent.setText(note.content)
        holder.itemUpdateBtn.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                putExtra("node_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.itemDelBtn.setOnClickListener {
            db.delNoteById(note.id)
            refreshData(db.getAllNotes())
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun refreshData(newNodes:List<Note>){
        notes = newNodes
        notifyDataSetChanged()
    }
}