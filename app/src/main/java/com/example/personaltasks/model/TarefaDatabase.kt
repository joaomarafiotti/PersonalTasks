package com.example.personaltasks.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Classe de configuração do banco de dados Room.
 * Define quais entidades fazem parte do banco e os conversores utilizados.
 */
@Database(entities = [Tarefa::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class TarefaDatabase : RoomDatabase() {

    /**
     * Fornece a instância do DAO para executar operações no banco.
     */
    abstract fun tarefaDao(): TarefaDao

    companion object {
        // Singleton para evitar múltiplas instâncias do banco.
        @Volatile
        private var INSTANCE: TarefaDatabase? = null

        /**
         * Obtém a instância do banco de dados.
         * Se não existir, cria uma nova.
         */
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
