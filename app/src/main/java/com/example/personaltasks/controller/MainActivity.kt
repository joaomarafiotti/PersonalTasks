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

/**
 * Activity principal que exibe a lista de Tarefas cadastradas.
 * Possui menu de opções para adicionar nova tarefa e menu de contexto para editar, excluir ou visualizar detalhes.
 */
class MainActivity : AppCompatActivity() {

    // Toolbar que exibe o título e o menu de opções (ignore esse comentario, somente para teste)
    private lateinit var toolbar: Toolbar

    // Mensagem exibida quando a lista de tarefas está vazia
    private lateinit var mensagemVazio: TextView

    // RecyclerView para exibir as tarefas
    private lateinit var recyclerView: RecyclerView

    // Adapter que gerencia a exibição das tarefas
    private lateinit var adapter: TarefaAdapter

    // Repositório para acessar o banco de dados
    private lateinit var repository: TarefaRepository

    // Guarda a posição da tarefa selecionada no clique longo
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configura a Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inicializa a mensagem de lista vazia
        mensagemVazio = findViewById(R.id.mensagemVazio)

        // Configura o RecyclerView
        recyclerView = findViewById(R.id.rvTarefas)
        adapter = TarefaAdapter(listOf()) { pos ->
            selectedPosition = pos // Armazena a posição clicada
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializa o repositório
        repository = TarefaRepository(this)

        // Carrega a lista de tarefas
        carregarTarefas()

        // Registra o RecyclerView para ter menu de contexto
        registerForContextMenu(recyclerView)
    }

    /**
     * Carrega as tarefas do banco de dados e atualiza a RecyclerView.
     * Exibe ou esconde a mensagem de lista vazia conforme necessário.
     */
    private fun carregarTarefas() {
        lifecycleScope.launch {
            val tarefas = repository.getAll()
            adapter.atualizarLista(tarefas)

            mensagemVazio.visibility = if (tarefas.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    /**
     * Recarrega a lista sempre que a Activity é retomada.
     */
    override fun onResume() {
        super.onResume()
        carregarTarefas()
    }

    /**
     * Infla o menu de opções na Toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Trata o clique no menu de opções.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_nova_tarefa -> {
                // Abre a Activity de Cadastro para criar nova Tarefa
                val intent = Intent(this, CadastroActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Infla o menu de contexto (após clique longo).
     */
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contexto, menu)
    }

    /**
     * Trata as ações do menu de contexto: editar, excluir ou mostrar detalhes.
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = adapter.tarefas[selectedPosition]

        return when (item.itemId) {
            R.id.action_editar -> {
                // Abre a tela de Cadastro já com a Tarefa para edição
                val intent = Intent(this, CadastroActivity::class.java)
                intent.putExtra("tarefa", tarefa)
                startActivity(intent)
                true
            }
            R.id.action_excluir -> {
                // Exclui a tarefa do banco e recarrega a lista
                lifecycleScope.launch {
                    repository.delete(tarefa)
                    carregarTarefas()
                }
                true
            }
            R.id.action_detalhes -> {
                // Exibe os detalhes da tarefa em um Toast
                val mensagem = "Título: ${tarefa.titulo}\nDescrição: ${tarefa.descricao}\nData: ${tarefa.dataLimite}"
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
