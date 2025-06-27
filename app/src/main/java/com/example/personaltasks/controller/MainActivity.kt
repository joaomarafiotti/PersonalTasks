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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TarefaAdapter
import com.example.personaltasks.helper.FirebaseAuthHelper

/**
 * Activity principal que exibe a lista de Tarefas cadastradas.
 * Possui menu de opções para adicionar nova tarefa e menu de contexto para editar, excluir ou visualizar detalhes.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var mensagemVazio: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarefaAdapter
    private lateinit var controller: MainController
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifica autenticação do usuário
        val authHelper = FirebaseAuthHelper()
        if (authHelper.getCurrentUser() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mensagemVazio = findViewById(R.id.mensagemVazio)

        recyclerView = findViewById(R.id.rvTarefas)
        adapter = TarefaAdapter(listOf()) { pos -> selectedPosition = pos }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        controller = MainController { tarefas ->
            runOnUiThread {
                adapter.atualizarLista(tarefas)
                mensagemVazio.visibility = if (tarefas.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        controller.carregarTarefas()
        registerForContextMenu(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        controller.carregarTarefas()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_nova_tarefa -> {
                startActivity(Intent(this, CadastroActivity::class.java))
                true
            }
            R.id.action_tarefas_excluidas -> {
                startActivity(Intent(this, ExcludedTasksActivity::class.java))
                true
            }
            R.id.action_logout -> {
                FirebaseAuthHelper().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contexto_ativas, menu)
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
                controller.excluirTarefa(tarefa)
                controller.carregarTarefas()
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