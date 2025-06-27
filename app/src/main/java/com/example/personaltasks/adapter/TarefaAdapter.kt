package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.model.Tarefa

/**
 * ViewHolder que representa cada item da lista de Tarefas.
 * Mantém as referências para as Views que serão atualizadas.
 */
class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
    val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricao)
    val tvDataLimite: TextView = itemView.findViewById(R.id.tvDataLimite)
    val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    val tvPrioridade: TextView = itemView.findViewById(R.id.tvPrioridade)
}

/**
 * Adapter responsável por exibir a lista de Tarefas no RecyclerView.
 * Recebe uma lista de tarefas e uma função lambda para tratar cliques longos.
 */
class TarefaAdapter(
    var tarefas: List<Tarefa>,
    private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<TarefaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]
        val status = if (tarefa.cumprida) "Cumprida" else "Pendente"

        holder.tvTitulo.text = tarefa.titulo
        holder.tvDescricao.text = tarefa.descricao
        holder.tvDataLimite.text = tarefa.dataLimite.toString()
        holder.tvStatus.text = "Status: $status"
        holder.tvPrioridade.text = tarefa.prioridade
        holder.itemView.setOnLongClickListener {
            onItemLongClick(position)
            false
        }
    }

    override fun getItemCount(): Int = tarefas.size

    fun atualizarLista(novasTarefas: List<Tarefa>) {
        tarefas = novasTarefas
        notifyDataSetChanged()
    }
}
