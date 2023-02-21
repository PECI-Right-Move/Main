package com.example.rightmove

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView


class ChecklistAdapter(private val context: Context, private val pieces: Array<String>) :
    RecyclerView.Adapter<ChecklistAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.checklist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkbox.text = pieces[position]
    }

    override fun getItemCount() = pieces.size
}
