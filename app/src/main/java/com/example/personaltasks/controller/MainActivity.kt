package com.example.personaltasks.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TarefaAdapter
import com.example.personaltasks.model.Tarefa
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = findViewById(R.id.rvTarefas)
        adapter = TarefaAdapter(listOf()) // inicialmente lista vazia
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TarefaAdapter(carregarTarefas())
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarefaAdapter

    private fun carregarTarefas(): List<Tarefa> {
        return listOf(
            Tarefa(titulo = "Estudar", descricao = "Revisar Kotlin", dataLimite = LocalDate.now().plusDays(2)),
            Tarefa(titulo = "Projeto", descricao = "Finalizar App", dataLimite = LocalDate.now().plusDays(5))
        )
    }
}
