package com.example.personaltasks.controller

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TarefaAdapter
import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.repository.TarefaRemoteRepository

class TarefasExcluidasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mensagemVazio: TextView
    private lateinit var adapter: TarefaAdapter
    private val repository = TarefaRemoteRepository()
    private var tarefas = listOf<Tarefa>()
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_excluidas)

        recyclerView = findViewById(R.id.rvTarefasExcluidas)
        mensagemVazio = findViewById(R.id.tvSemExcluidas)

        adapter = TarefaAdapter(tarefas) { pos -> selectedPosition = pos }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        registerForContextMenu(recyclerView)
        carregarTarefasExcluidas()
    }

    private fun carregarTarefasExcluidas() {
        repository.getExcluidas { lista ->
            tarefas = lista
            adapter.atualizarLista(lista)
            mensagemVazio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contexto_excluidas, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = tarefas[selectedPosition]

        return when (item.itemId) {
            R.id.action_reativar -> {
                tarefa.excluida = false
                repository.update(tarefa)
                carregarTarefasExcluidas()
                true
            }
            R.id.action_detalhes -> {
                val msg = "Título: ${tarefa.titulo}\nDescrição: ${tarefa.descricao}\nData: ${tarefa.dataLimite}"
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}