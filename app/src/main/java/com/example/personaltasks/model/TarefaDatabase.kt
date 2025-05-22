package com.example.personaltasks.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Classe de configuração do banco de dados Room.
 * Define as entidades e os conversores utilizados.
 */
@Database(entities = [Tarefa::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class TarefaDatabase : RoomDatabase() {

    /**
     * Metodo abstrato para acessar o DAO das Tarefas.
     */
    abstract fun tarefaDao(): TarefaDao
}
