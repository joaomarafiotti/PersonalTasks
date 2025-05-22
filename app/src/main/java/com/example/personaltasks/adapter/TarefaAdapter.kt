package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Tarefa

/**
 * ViewHolder para representar cada item da lista de tarefas.
 */
class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
    val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
    val tvDataLimite: TextView = itemView.findViewById(R.id.tvDataLimite)
}

/**
 * Adapter para exibir a lista de Tarefas no RecyclerView.
 */
class TarefaAdapter(
    private val tarefas: List<Tarefa>
) : RecyclerView.Adapter<TarefaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]
        holder.tvTitulo.text = tarefa.titulo
        holder.tvDescricao.text = tarefa.descricao
        holder.tvDataLimite.text = tarefa.dataLimite.toString()
    }

    override fun getItemCount(): Int = tarefas.size
}
