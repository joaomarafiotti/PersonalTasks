package com.example.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Entity(tableName = "tarefas")
@Parcelize
data class Tarefa(
    @PrimaryKey var id: String = "",
    var titulo: String = "",
    var descricao: String = "",
    var dataLimite: LocalDate = LocalDate.now(),
    var cumprida: Boolean = false,
    var excluida: Boolean = false,
    var prioridade: String = "Média"
) : Parcelable {
    constructor() : this("", "", "", LocalDate.now(), false, false, "Média")
}
