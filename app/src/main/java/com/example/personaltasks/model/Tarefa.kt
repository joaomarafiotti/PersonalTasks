package com.example.personaltasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

/**
 * Modelo de dados usado tanto localmente quanto remotamente.
 * No Firebase, os campos 'id' e 'excluida' são essenciais para manipulação e histórico.
 */
@Parcelize
data class Tarefa(
    var id: String = "",    // Firebase usa String como chave
    var titulo: String = "",
    var descricao: String = "",
    var dataLimite: LocalDate = LocalDate.now(),
    var cumprida: Boolean = false,
    var excluida: Boolean = false   // Marca se a tarefa foi "excluída"
) : Parcelable {

    // Firebase exige construtor sem argumentos
    constructor() : this("", "", "", LocalDate.now(), false, false)
}
