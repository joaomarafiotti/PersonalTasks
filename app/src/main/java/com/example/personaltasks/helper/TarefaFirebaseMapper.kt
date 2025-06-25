package com.example.personaltasks.helper

import com.example.personaltasks.model.Tarefa
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Utilitário para conversão entre objetos Tarefa e Map<String, Any> para Firebase.
 */
object TarefaFirebaseMapper {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun toMap(tarefa: Tarefa): Map<String, Any> {
        return mapOf(
            "id" to tarefa.id,
            "titulo" to tarefa.titulo,
            "descricao" to tarefa.descricao,
            "dataLimite" to tarefa.dataLimite.format(formatter),
            "cumprida" to tarefa.cumprida,
            "excluida" to tarefa.excluida
        )
    }

    fun fromMap(map: Map<String, Any?>): Tarefa {
        return Tarefa(
            id = map["id"] as? String ?: "",
            titulo = map["titulo"] as? String ?: "",
            descricao = map["descricao"] as? String ?: "",
            dataLimite = LocalDate.parse(map["dataLimite"] as? String ?: "", formatter),
            cumprida = map["cumprida"] as? Boolean ?: false,
            excluida = map["excluida"] as? Boolean ?: false
        )
    }
}