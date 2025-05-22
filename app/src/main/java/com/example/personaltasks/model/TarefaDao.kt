package com.example.personaltasks.model

import androidx.room.*

/**
 * Interface DAO para operações no banco de dados de Tarefas.
 * @Dao indica que esta interface define metodos de acesso aos dados.
 */

@Dao
interface TarefaDao {
    /**
     * Insere uma nova Tarefa no banco.
     * Retorna o ID gerado.
     */
    @Insert
    suspend fun insert(tarefa: Tarefa): Long

    /**
     * Atualiza uma Tarefa existente.
     */
    @Update
    suspend fun update(tarefa: Tarefa)

    /**
     * Exclui uma Tarefa.
     */
    @Delete
    suspend fun delete(tarefa: Tarefa)

    /**
     * Retorna todas as Tarefas cadastradas, ordenadas pela data limite.
     */
    @Query("SELECT * FROM tarefas ORDER BY dataLimite ASC")
    suspend fun getAll(): List<Tarefa>

    /**
     * Busca uma Tarefa pelo ID.
     */
    @Query("SELECT * FROM tarefas WHERE id = :id")
    suspend fun getById(id: Int): Tarefa
}
