package com.example.personaltasks.controller

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TarefaAdapter
import com.example.personaltasks.repository.TarefaRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarefaAdapter
    private lateinit var repository: TarefaRepository
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvTarefas)
        adapter = TarefaAdapter(listOf()) { pos ->
            selectedPosition = pos
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        carregarTarefas()  // depois carrega as tarefas reais
        repository = TarefaRepository(this)
        registerForContextMenu(recyclerView)

        recyclerView.setOnLongClickListener {
            false
        }
    }

    private fun carregarTarefas() {
        lifecycleScope.launch {
            val tarefas = repository.getAll()
            adapter.atualizarLista(tarefas)
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
        return when (item.itemId) {
            R.id.action_editar -> {
                // TODO: implementar lógica de editar
                true
            }
            R.id.action_excluir -> {
                // TODO: implementar lógica de excluir
                true
            }
            R.id.action_detalhes -> {
                // TODO: implementar lógica de detalhes
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
