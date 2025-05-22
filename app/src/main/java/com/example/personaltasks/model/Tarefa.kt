package com.example.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

/**
 * Entidade que representa uma Tarefa no banco de dados.
 * @Entity define que essa classe sera uma tabela.
 * @Parcelize permite que possamos enviar a Tarefa entre Activities.
 */

@Parcelize
@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // ID autogerado pelo Room

    val titulo: String,
    val descricao: String,
    val dataLimite: LocalDate

) : Parcelable
