package com.example.personaltasks.model

import androidx.room.*

/**
 * Interface DAO (Data Access Object) que define as operações de acesso ao banco de dados.
 * O Room gera automaticamente a implementação destas funções.
 */
@Dao
interface TarefaDao {

    /**
     * Insere uma nova Tarefa no banco.
     * O Room cuida de gerar o SQL para esta operação.
     */
    @Insert
    suspend fun insert(tarefa: Tarefa): Long

    /**
     * Atualiza uma Tarefa existente no banco.
     */
    @Update
    suspend fun update(tarefa: Tarefa)

    /**
     * Exclui uma Tarefa do banco.
     */
    @Delete
    suspend fun delete(tarefa: Tarefa)

    /**
     * Recupera todas as Tarefas, ordenadas pela data limite (ascendente).
     */
    @Query("SELECT * FROM tarefas ORDER BY dataLimite ASC")
    suspend fun getAll(): List<Tarefa>

    /**
     * Busca uma Tarefa específica pelo ID.
     */
    @Query("SELECT * FROM tarefas WHERE id = :id")
    suspend fun getById(id: Int): Tarefa
}
