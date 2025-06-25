package com.example.personaltasks.repository

import com.example.personaltasks.model.Tarefa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TarefaRemoteRepository {

    private val auth = FirebaseAuth.getInstance()
    private val database: DatabaseReference
        get() = FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(auth.currentUser?.uid ?: "anonimo")
            .child("tarefas")

    fun getAll(callback: (List<Tarefa>) -> Unit) {
        database.orderByChild("excluida").equalTo(false)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tarefas = mutableListOf<Tarefa>()
                    for (child in snapshot.children) {
                        val tarefa = child.getValue(Tarefa::class.java)
                        tarefa?.let {
                            it.id = child.key!! // garante o ID
                            tarefas.add(it)
                        }
                    }
                    callback(tarefas)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
    }

    fun getExcluidas(callback: (List<Tarefa>) -> Unit) {
        database.orderByChild("excluida").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tarefas = mutableListOf<Tarefa>()
                    for (child in snapshot.children) {
                        val tarefa = child.getValue(Tarefa::class.java)
                        tarefa?.let {
                            it.id = child.key!!
                            tarefas.add(it)
                        }
                    }
                    callback(tarefas)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
    }

    fun insert(tarefa: Tarefa) {
        val novaRef = database.push()
        tarefa.id = novaRef.key!!
        novaRef.setValue(tarefa)
    }

    fun update(tarefa: Tarefa) {
        if (tarefa.id.isNotEmpty()) {
            database.child(tarefa.id).setValue(tarefa)
        }
    }

    fun delete(tarefa: Tarefa) {
        if (tarefa.id.isNotEmpty()) {
            tarefa.excluida = true
            database.child(tarefa.id).setValue(tarefa)
        }
    }
}
