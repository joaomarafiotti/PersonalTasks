package com.example.personaltasks.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaltasks.R
import com.example.personaltasks.adapter.TarefaAdapter
import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.repository.TarefaRepository
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TarefaAdapter
    private lateinit var repository: TarefaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvTarefas)
        adapter = TarefaAdapter(carregarTarefas())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        repository = TarefaRepository(this)
    }

    private fun carregarTarefas(): List<Tarefa> {
        return listOf(
            Tarefa(
                titulo = "Estudar",
                descricao = "Revisar Kotlin",
                dataLimite = LocalDate.now().plusDays(2)
            ),
            Tarefa(
                titulo = "Projeto",
                descricao = "Finalizar App",
                dataLimite = LocalDate.now().plusDays(5)
            )
        )
    }
}
