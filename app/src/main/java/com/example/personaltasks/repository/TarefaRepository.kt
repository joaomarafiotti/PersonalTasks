package com.example.personaltasks.repository

import android.content.Context
import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.model.TarefaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Classe responsável por gerenciar as operações de banco de dados relacionadas às Tarefas.
 */
class TarefaRepository(context: Context) {

    private val db = TarefaDatabase.getDatabase(context).tarefaDao()

    /**
     * Obtém todas as tarefas salvas no banco.
     */
    suspend fun getAll(): List<Tarefa> = withContext(Dispatchers.IO) {
        db.getAll()
    }

    /**
     * Insere uma nova tarefa no banco.
     */
    suspend fun insert(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        db.insert(tarefa)
    }

    /**
     * Atualiza uma tarefa existente.
     */
    suspend fun update(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        db.update(tarefa)
    }

    /**
     * Deleta uma tarefa.
     */
    suspend fun delete(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        db.delete(tarefa)
    }
}
