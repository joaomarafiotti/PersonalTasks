package com.example.personaltasks.controller

import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.repository.TarefaRemoteRepository

class MainController(private val callback: (List<Tarefa>) -> Unit) {

    private val repository = TarefaRemoteRepository()

    fun carregarTarefas() {
        repository.observeAll { tarefas ->
            callback(tarefas)
        }
    }

    fun excluirTarefa(tarefa: Tarefa) {
        repository.delete(tarefa)
    }
}
