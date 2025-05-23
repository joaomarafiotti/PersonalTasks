package com.example.personaltasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
    var tarefas: List<Tarefa>,
    private val onItemLongClick: (Int) -> Unit  // função lambda para clique longo
) : RecyclerView.Adapter<TarefaViewHolder>() {

    var selectedPosition: Int = -1  // armazena a posição do item selecionado

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

        // Clique longo → aciona callback e registra ContextMenu
        holder.itemView.setOnLongClickListener {
            onItemLongClick(position)
            selectedPosition = position
            false  // importante: permite o ContextMenu ser exibido
        }

        holder.itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
            (v.context as? AppCompatActivity)?.menuInflater?.inflate(R.menu.menu_contexto, menu)
        }
    }

    override fun getItemCount(): Int = tarefas.size

    fun atualizarLista(novasTarefas: List<Tarefa>) {
        tarefas = novasTarefas
        notifyDataSetChanged()
    }
}
