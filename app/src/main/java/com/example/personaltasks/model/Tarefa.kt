package com.example.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

/**
 * Entidade que representa uma Tarefa no banco de dados.
 *
 * @Entity define que essa classe será convertida em uma tabela no Room.
 * @Parcelize permite passar objetos Tarefa entre Activities através de Intents.
 */
@Parcelize
@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // ID autogerado pelo Room

    val titulo: String,
    val descricao: String,
    val dataLimite: LocalDate,
    val cumprida: Boolean = false
) : Parcelable

