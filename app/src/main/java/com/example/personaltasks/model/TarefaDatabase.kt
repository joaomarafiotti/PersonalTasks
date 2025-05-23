package com.example.personaltasks.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var INSTANCE: TarefaDatabase? = null

        fun getDatabase(context: Context): TarefaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarefaDatabase::class.java,
                    "tarefa_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
