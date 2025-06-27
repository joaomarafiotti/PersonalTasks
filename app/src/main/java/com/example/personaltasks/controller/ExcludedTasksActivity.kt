package com.example.personaltasks.controller

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TarefaAdapter
import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.repository.TarefaRemoteRepository

/**
 * Tela que lista tarefas excluídas, permitindo visualizar detalhes ou reativar.
 */
class ExcludedTasksActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var mensagemVazio: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarefaAdapter
    private lateinit var repository: TarefaRemoteRepository
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excluded_tasks)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tarefas Excluídas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // ← habilita botão voltar

        mensagemVazio = findViewById(R.id.mensagemVazio)
        recyclerView = findViewById(R.id.rvTarefasExcluidas)

        adapter = TarefaAdapter(listOf()) { pos -> selectedPosition = pos }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        repository = TarefaRemoteRepository()
        registerForContextMenu(recyclerView)
        carregarTarefasExcluidas()
    }

    private fun carregarTarefasExcluidas() {
        repository.getExcluidas { tarefas ->
            runOnUiThread {
                adapter.atualizarLista(tarefas)
                mensagemVazio.visibility = if (tarefas.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contexto, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = adapter.tarefas[selectedPosition]

        return when (item.itemId) {
            R.id.action_detalhes -> {
                val mensagem = "Título: ${tarefa.titulo}\nDescrição: ${tarefa.descricao}\nData: ${tarefa.dataLimite}"
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_reativar -> {
                tarefa.excluida = false
                repository.update(tarefa)
                carregarTarefasExcluidas()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    /**
     * Trata o clique no botão de voltar (⬅)
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}