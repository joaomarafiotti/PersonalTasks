package com.example.personaltasks.controller

import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.repository.TarefaRemoteRepository

/**
 * Controller da tela principal que gerencia a lista de tarefas ativas.
 * Responsável por carregar e excluir tarefas com persistência remota.
 */
class MainController(private val callback: (List<Tarefa>) -> Unit) {

    private val repository = TarefaRemoteRepository()

    fun carregarTarefas() {
        // Agora busca somente tarefas com excluida = false
        repository.getAtivas { tarefas ->
            callback(tarefas)
        }
    }

    fun excluirTarefa(tarefa: Tarefa) {
        // Exclusão lógica via Firebase
        repository.delete(tarefa)
    }
}