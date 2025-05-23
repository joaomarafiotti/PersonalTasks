package com.example.personaltasks.controller

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TarefaAdapter
import com.example.personaltasks.repository.TarefaRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var mensagemVazio: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarefaAdapter
    private lateinit var repository: TarefaRepository
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mensagemVazio = findViewById(R.id.mensagemVazio)
        recyclerView = findViewById(R.id.rvTarefas)

        adapter = TarefaAdapter(listOf()) { pos ->
            selectedPosition = pos
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        repository = TarefaRepository(this)

        carregarTarefas()

        registerForContextMenu(recyclerView)
    }

    private fun carregarTarefas() {
        lifecycleScope.launch {
            val tarefas = repository.getAll()
            adapter.atualizarLista(tarefas)

            mensagemVazio.visibility = if (tarefas.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        carregarTarefas()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_nova_tarefa -> {
                val intent = Intent(this, CadastroActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contexto, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = adapter.tarefas[selectedPosition]

        return when (item.itemId) {
            R.id.action_editar -> {
                val intent = Intent(this, CadastroActivity::class.java)
                intent.putExtra("tarefa", tarefa)
                startActivity(intent)
                true
            }
            R.id.action_excluir -> {
                lifecycleScope.launch {
                    repository.delete(tarefa)
                    carregarTarefas()
                }
                true
            }
            R.id.action_detalhes -> {
                val mensagem = "Título: ${tarefa.titulo}\nDescrição: ${tarefa.descricao}\nData: ${tarefa.dataLimite}"
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
