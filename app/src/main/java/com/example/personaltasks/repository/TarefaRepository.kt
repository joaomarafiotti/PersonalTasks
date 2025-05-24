package com.example.personaltasks.repository

import android.content.Context
import com.example.personaltasks.model.Tarefa
import com.example.personaltasks.model.TarefaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Classe responsável por gerenciar as operações de banco de dados relacionadas às Tarefas.
 * Atua como camada intermediária entre o Controller (Activity) e o DAO.
 *
 * Boa prática: isola o acesso ao banco, facilitando manutenção e testes.
 */
class TarefaRepository(context: Context) {

    // Instância do DAO obtida a partir do banco de dados
    private val db = TarefaDatabase.getDatabase(context).tarefaDao()

    /**
     * Obtém todas as tarefas cadastradas no banco de dados.
     * Executa em Dispatcher.IO para não bloquear a UI.
     */
    suspend fun getAll(): List<Tarefa> = withContext(Dispatchers.IO) {
        db.getAll()
    }

    /**
     * Insere uma nova tarefa no banco de dados.
     * Executa de forma assíncrona no Dispatcher.IO.
     */
    suspend fun insert(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        db.insert(tarefa)
    }

    /**
     * Atualiza uma tarefa existente no banco.
     * Executa de forma assíncrona no Dispatcher.IO.
     */
    suspend fun update(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        db.update(tarefa)
    }

    /**
     * Exclui uma tarefa do banco de dados.
     * Executa de forma assíncrona no Dispatcher.IO.
     */
    suspend fun delete(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        db.delete(tarefa)
    }
}
