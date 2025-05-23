package com.example.personaltasks.controller

import android.os.Bundle
import android.view.Menu
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvTarefas)
        adapter = TarefaAdapter(listOf())  // inicialmente lista vazia
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        carregarTarefas()  // depois carrega as tarefas reais
        repository = TarefaRepository(this)
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

}
