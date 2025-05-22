package com.example.personaltasks.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R

/**
 * ViewHolder para representar cada item da lista de tarefas.
 */

class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
    val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
    val tvDataLimite: TextView = itemView.findViewById(R.id.tvDataLimite)
}
